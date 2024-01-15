package com.agilecrm_automation.stepdefinition;

import com.agilecrm_automation.pojo.CreateContactPojo;
import com.agilecrm_automation.pojo.CreateContactResponsePojo;
import com.github.javafaker.Faker;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ContactStepDef {

    RequestSpecification specification;
    Response response;
    String firstName;
    String lastName;

    String contactEmail;
    List<Long> idList;
    Faker faker= new Faker(new Random());
    Random random;
    CreateContactPojo createContactPojo;
    CreateContactResponsePojo createContactResponsePojo;
    Long contactId;

    @Given("I setup a request structure to get contact information")
    public void setup(){
        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com")
                .basePath("/dev/api/contacts")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .header("Accept","application/json")
                .log().all();
    }


    @When("I hit api of get all Contact")
    public void iHitApiOfGetAllContact() {
       response = specification.get();
    }

    @Then("I verify Contact response")
    public void iVerifyContactResponse() {
        response.prettyPrint();
        idList = response.jsonPath().getList("id");

        int statusCode = response.getStatusCode();

        Assert.assertEquals(200,statusCode);

        List<Object> contactList = response.jsonPath().getList("");

        for(Object list : contactList){

            Map<String,Object> mapObj = (Map<String,Object>)list;

            Object object = mapObj.get("properties");

            List<Object> obj = (List<Object>)object;

          for(Object objList : obj){

              Map<String,String> objMap =(Map<String,String>) objList;

              String name = objMap.get("name");
              if(name.equals("last_name")){
                 String id =mapObj.get("id").toString();
                  System.out.println(id);
              }
          }
        }
    }


    @When("I hit an api of get contact by id")
    public void iHitAnApiOfGetContactById() {
       response=specification.get("4898708905590784");
    }

    @Then("I verify Contact by id response")
    public void iVerifyContactByIdResponse() {
        response.prettyPrint();
        Assert.assertEquals(200,response.getStatusCode());

        long id = response.jsonPath().getLong("id");
        Assert.assertEquals(4898708905590784l,id);
    }


    @When("I hit an api of get contact by ID")
    public void iHitAnApiOfGetContactByID() {
      //  idList = response.jsonPath().getList("id");
        if(!idList.isEmpty()) {
            response = specification.get("/" + idList.get(1));
                  }
        else{
            response = specification.get("/4898708905590784");
        }




    }

    @Then("I verify contact by id in response")
    public void uVerifyContactByIdInResponse() {
        response.prettyPrint();
       Long idlist = response.jsonPath().getLong("id");


       if(!idList.isEmpty()) {
           Assert.assertEquals(idList.get(1), idlist);
       }
//       else{
//           for(int i=0; i<idlist.size();i++){
//           Assert.assertEquals(4898708905590784l, (long)idlist.get(i));}
//       }
    }


    @When("I setup request structure to get contact by id")
    public void iSetupRequestStructureToGetContactById() {

        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com")
                .basePath("/dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .header("accept","application/json")
                .log().all();

        if(!idList.isEmpty() && idList!=null)
        {
            long pathpara= idList.get(1);
           // System.out.println(pathpara);
            specification.pathParam("contactId",pathpara);
        }

    }

    @And("I hit an api of get contact by id with path param")
    public void iHitAnApiOfGetContactByIdWithPathParam() {

        response=specification.get("/contacts/{contactId}");
    }


    @Then("I verify contact by id in response as pathparam")
    public void iVerifyContactByIdInResponseAsPathparam() {
       response.prettyPrint();
       long actPara = response.jsonPath().getLong("id");
       long expPara = idList.get(1);
        Assert.assertEquals(expPara,actPara);
    }

    @When("I setup request structure to get contact by id using loop")
    public void iSetupRequestStructureToGetContactByIdUsingLoop() {
        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com")
                .basePath("/dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .header("accept","application/json")
                .log().all();

        if(!idList.isEmpty() && idList!=null)
        {
            for(int i=0; i<idList.size(); i++) {
               // long pathpar = idList.get(i);
               // System.out.println(pathpar);
                specification.pathParam("contId", idList.get(i));
            }
        }

    }


    @And("I hit an api of get contact by id with path param using loop")
    public void iHitAnApiOfGetContactByIdWithPathParamUsingLoop() {


        response = specification.get("/contacts/{contId}");


    }

    @Then("I verify contact by id in response as iteration of pathParam")
    public void iVerifyContactByIdInResponseAsIterationOfPathParam() {
       response.prettyPrint();
      //  long l = response.jsonPath().get("id");
       // System.out.println(l);
    }


    @Given("I setup a request structure to create contact with following info")
    public void iSetupARequestStructureToCreateContact(Map<String,String> dataMap) {
        firstName = dataMap.get("firstname");
        lastName  = dataMap.get("lastname");

        System.out.println(firstName +" : "+ lastName);

        String reqBody= "{\n" +
//                "    \"star_value\": \"3\",\n" +
//                "    \"lead_score\": \"110\",\n" +
//                "    \"tags\": [\n" +
//                "        \"one down\"\n" +
//                "        \n" +
//                "    ],\n" +
                "    \"properties\": [\n" +
                "        {\n" +
                "            \"type\": \"SYSTEM\",\n" +
                "            \"name\": \"first_name\",\n" +
                "            \"value\": \""+firstName+"\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"SYSTEM\",\n" +
                "            \"name\": \"last_name\",\n" +
                "            \"value\": \""+lastName+"\"\n" +
                "        },\n" +
//                "        {\n" +
//                "            \"type\": \"SYSTEM\",\n" +
//                "            \"name\": \"email\",\n" +
//                "            \"subtype\": \"work\",\n" +
//                "            \"value\": \"viratkohli@india.com\"\n" +
//                "        },\n" +
                "        {\n" +
                "            \"type\": \"SYSTEM\",\n" +
                "            \"name\": \"address\",\n" +
                "            \"value\": \"{\\\"address\\\":\\\"225 George Street\\\",\\\"city\\\":\\\"NSW\\\",\\\"state\\\":\\\"Sydney\\\",\\\"zip\\\":\\\"2000\\\",\\\"country\\\":\\\"Australia\\\"}\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";



        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(reqBody)
                .auth().basic("Rest@yopmail.com", "neie86ad56qv3ku0mn9o16ogg5")
                .log().all();

    }

    @When("I hit a create contact api")
    public void iHitACreateContactApi() {
        response = specification.post("contacts");
    }

    @Then("I verify contact created successfully")
    public void iVerifyContactCreatedSuccessfully() {
        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

       String actType  = response.jsonPath().get("type");
       String expType = "PERSON";
       Assert.assertEquals(expType,actType);


      List<Map<String,String>> dataMap =  response.jsonPath().getList("properties");
      for(Map<String,String> map : dataMap){
          if(map.get("name").equals("first_name")){
              String actFirstName = map.get("value");

              Assert.assertEquals(firstName,actFirstName);
          }else if(map.get("name").equals("last_name")){
              String actLastName = map.get("value");
              Assert.assertEquals(lastName,actLastName);
          }

      }
    }

    @Given("I setup a request structure to create contact with following hashmap information")
    public void iSetupARequestStructureToCreateContactWithFollowingHashmapInformation(DataTable table) {

        List<Map<String,String>> dataList = table.asMaps();

        Map<String,String> data = dataList.get(0);
      //  System.out.println(data);

        firstName = data.get("firstname");
        lastName = data.get("lastname");
        String Email = data.get("email");

        Map<String, Object> reqBody= new HashMap<>();
        reqBody.put("type","PERSON");

        List<Map<String,String>> propObject = new ArrayList<>();

        Map<String,String> map1 = new HashMap<>();
        map1.put("type","SYSTEM");
        map1.put("name","first_name");

        Map<String,String> map2 = new HashMap<>();
        map2.put("type","SYSTEM");
        map2.put("name","last_name");

        Map<String, String> map3 = new HashMap<>();
        map3.put("type","SYSTEM");
        map3.put("name","email");
        map3.put("subtype","work");


        if(firstName==null){
            map1.put("value",null);
            map2.put("value",lastName);
            map3.put("value",Email);
        }
        else if(lastName==null){
            map1.put("value",firstName);
            map2.put("value",null);
            map3.put("value",Email);
        } else if (Email==null) {
            map1.put("value",firstName);
            map2.put("value",lastName);
            map3.put("value",null);
        }else if (firstName==null && lastName==null && Email==null){
            map1.put("value",null);
            map2.put("value",null);
            map3.put("value",null);
        }
        else if((firstName==null && lastName==null ) || (firstName==null && Email==null) ||(lastName==null && Email==null)) {
            map1.put("value",firstName);
            map2.put("value",lastName);
            map3.put("value",Email);
        }
        else{
            map1.put("value",firstName);
            map2.put("value",lastName);
            map3.put("value",Email);
        }
        propObject.add(map1);
        propObject.add(map2);
        propObject.add(map3);

        reqBody.put("properties",propObject);

        specification=RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(reqBody)
                .log().all();

    }

    @When("I hit an Post Api to create Contact")
    public void iHitAnPostApiToCreateContact() {
        response = specification.post("contacts");
    }

    @Then("I verify with Contact created with {int} status code")
    public void iVerifyWithContactCreatedWithStatusCodeStatusCode(int statusCode) {
        response.prettyPrint();
        Assert.assertEquals(statusCode,response.getStatusCode());
    }

    @Given("I setup a request structure to create contact with file info")
    public void iSetupARequestStructureToCreateContactWithFileInfo() throws IOException, ParseException {

        String filePath = System.getProperty("user.dir")+"/src/test/resources/RequestFiles/CreateContactRequest.json";
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        JSONParser jsonParser = new JSONParser();
        Object inputObject = jsonParser.parse(fileReader);
        JSONObject jsonObject = (JSONObject)inputObject;
        JSONArray jsonArray = (JSONArray)jsonObject.get("properties");

        JSONObject firstNameObject = (JSONObject)jsonArray.get(0);
        firstName = firstNameObject.get("value").toString();

        JSONObject lastNameObject = (JSONObject)jsonArray.get(1);
        lastName = lastNameObject.get("value").toString();

        JSONObject emailObject = (JSONObject)jsonArray.get(2);
        contactEmail = emailObject.get("value").toString();


        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(file)
                .log().all();

    }

    @Then("I verify contact created successfully with {int} status code")
    public void iVerifyContactCreatedSuccessfullyWithStatusCode(int statuscode) {
        response.prettyPrint();

        Assert.assertEquals(statuscode,response.getStatusCode());

        List<Map<String,String>> dataList = response.jsonPath().get("properties");
        for(Map<String,String> dataMap : dataList){
            if(dataMap.get("name").equals("first_name")) {
                String actFirstName = dataMap.get("value");
                Assert.assertEquals(firstName, actFirstName);
            }
            else if(dataMap.get("name").equals("last_name")){
                String actLastName = dataMap.get("value");
                Assert.assertEquals(lastName,actLastName);
            }
            else if(dataMap.get("name").equals("email")){
                String actUrl = dataMap.get("value");
                Assert.assertEquals(contactEmail,actUrl);
            }
        }

    }

    @Given("I setup a request structure to create contact")
    public void iSetupARequestStructureToCreateContact() {

        createContactPojo = new CreateContactPojo();

        Map<String,String> map1 = new HashMap<>();
        map1.put("type", "SYSTEM");
        map1.put("name", "first_name");
        map1.put("value","K L ");

        Map<String,String> map2 = new HashMap<>();
        map2.put("type", "SYSTEM");
        map2.put("name", "last_name");
        map2.put("value","Rahul");

        Map<String,String> map3 = new HashMap<>();
        map3.put("type", "SYSTEM");
        map3.put("name", "email");
        map3.put("subtype","work");
        random=new Random();

        faker = new Faker(random);
        map3.put("value", faker.name().firstName().toLowerCase()+"@india.com");

//        Map<String,String> map4 = new HashMap<>();
//        map4.put("type", "SYSTEM");
//        map4.put("name","address");
//        map4.put("value", "{\"address\":\"225 George Street\",\"city\":\"NSW\",\"state\":\"Sydney\",\"zip\":\"2000\",\"country\":\"Australia\"}");

        List<Map<String,String>> list = new ArrayList<>();

        list.add(map1);
        list.add(map2);
        list.add(map3);
//        list.add(map4);

        List<String> str =new ArrayList<> (List.of("one down","wicket Keeper"));

        createContactPojo.setStar_value("2");
        createContactPojo.setLead_score("135");
        createContactPojo.setTags(str);
        createContactPojo.setProperties(list);

        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(createContactPojo)
                .log().all();

    }

    @When("I hit an api to create contact")
    public void iHitAnApiToCreateContact() {
        response = specification.post("contacts");
    }


    @Then("I verify Contact Created successfully")
    public void iVerifyContactCreated() {
        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());
    }

    @Then("I verify Contact Created successfully with Deserialization")
    public void iVerifyContactCreatedSuccessfullyWithDeserialization() {
        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());
        createContactResponsePojo = response.as(CreateContactResponsePojo.class);

        List<Map<String,String>> actPropList = createContactResponsePojo.getProperties();
        for(Map<String,String> propMap : actPropList){
            String actName = propMap.get("name");
            String actVal = propMap.get("value");

            List<Map<String,String>> expPropList = createContactPojo.getProperties();
            for(Map<String,String> expPropMap : expPropList){
                String expName = expPropMap.get("name");
                String expVal = expPropMap.get("value");

                if(actName.equals(expName)){
                    Assert.assertEquals(expVal,actVal);
                }
            }

        }

        List<String> actTags = createContactResponsePojo.getTags();
        List<String> exptags = createContactPojo.getTags();

        Assert.assertEquals(exptags,actTags);

        String actStarVal = createContactResponsePojo.getStar_value();
        String expStarVal = createContactPojo.getStar_value();

        Assert.assertEquals(expStarVal,actStarVal);

        String actLeadScore = createContactResponsePojo.getLead_score();
        String expLeadScore = createContactPojo.getLead_score();

        Assert.assertEquals(expLeadScore,actLeadScore);

        contactId = createContactResponsePojo.getId();

    }

    @Then("I verify created contact in response with by contact id Api")
    public void iVerifyCreatedContactInResponseWithByContactIdApi() {

        specification=RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("contactId",contactId)
                .log().all();

        response = specification.get("contacts/{contactId}");

        response.prettyPrint();
        Assert.assertEquals(200,response.getStatusCode());

        createContactResponsePojo = response.as(CreateContactResponsePojo.class);
        List<Map<String,String>> propList =  createContactResponsePojo.getProperties();
        for(Map<String,String> propMap : propList){
           String actName =  propMap.get("name");
           String actValue = propMap.get("value");

           List<Map<String,String>> expPropList = createContactPojo.getProperties();
           for(Map<String,String> expPropMap : expPropList){
               String expName = expPropMap.get("name");
               String expValue = expPropMap.get("value");

               if(actName.equals(expName)){
                   Assert.assertEquals(expValue,actValue);
               }
           }
        }

        Long actId = createContactResponsePojo.getId();

        Assert.assertEquals(contactId,actId);

       List<String> actTags = createContactResponsePojo.getTags();
       List<String> expTags = createContactPojo.getTags();

       Assert.assertEquals(expTags,actTags);

       String actLeadScore = createContactResponsePojo.getLead_score();
       String expLeadScore = createContactPojo.getLead_score();
        System.out.println(actLeadScore + " : " +expLeadScore);
       Assert.assertEquals(expLeadScore,actLeadScore);

       String actStarVal = createContactResponsePojo.getStar_value();
       String expStarVal = createContactPojo.getStar_value();
        System.out.println(actStarVal + " : " + expStarVal);
       Assert.assertEquals(expStarVal,actStarVal);

    }

    @Then("I verify created contact in get all contact response")
    public void iVerifyCreatedContactInGetAllContactResponse() {
        specification =RestAssured.given();
        response=specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all().get("contacts");

        response.prettyPrint();
        Assert.assertEquals(200,response.getStatusCode());

        CreateContactResponsePojo[] responsePojo = response.as(CreateContactResponsePojo[].class);

      for( CreateContactResponsePojo resObject: responsePojo){

          Long actId = resObject.getId();

          if(actId.equals(contactId)) {

          //    System.out.println(actId);

              List<Map<String, String>> PropList = resObject.getProperties();
              for (Map<String, String> propMap : PropList) {
                  String actName = propMap.get("name");
                  String actValue = propMap.get("value");

                  List<Map<String, String>> expPropList = createContactPojo.getProperties();
                  for (Map<String, String> expPropMap : expPropList) {
                      String expName = expPropMap.get("name");
                      String expValue = expPropMap.get("value");

                      if (actName.equals(expName)) {
                          Assert.assertEquals(expValue, actValue);
                      }
                  }
              }

          }
      }
    }

    @Given("I setup a request to create Contact using request body using Serialization")
    public void iSetupARequestToCreateContactUsingRequestBodyUsingSerialization() {
        createContactPojo = new CreateContactPojo();

        createContactPojo.setStar_value("5");
        createContactPojo.setLead_score("110");
        List<String> tags = new ArrayList<>(List.of("Opener","left hand batsman"));
        createContactPojo.setTags(tags);
        List<Map<String,String>> propList = new ArrayList<>();
        Map<String,String> propMap1 = new HashMap<>(Map.of("type","SYSTEM","name","first_name",
                                                        "value",faker.name().firstName()));
        Map<String,String> propMap2 = new HashMap<>(Map.of("type","SYSTEM","name","last_name",
                                                        "value",faker.name().lastName()));
        Map<String,String> propMap3 = new HashMap<>(Map.of("type","SYSTEM","name","email",
                "subtype","work","value",faker.company().url()));
        propList.add(propMap1);
        propList.add(propMap2);
        propList.add(propMap3);
        createContactPojo.setProperties(propList);

        specification= RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(createContactPojo)
                .log().all();

    }

    @When("I hit an api to create Contact")
    public void iHitAnApiToCreateContactt() {
        response = specification.post("contacts");

    }

    @Then("I verify created Contact in the response using DeSerialization")
    public void iVerifyCreatedContactInTheResponseUsingDeSerialization() {
        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

        createContactResponsePojo = response.as(CreateContactResponsePojo.class);

        List<Map<String,String>> actPropList = createContactResponsePojo.getProperties();

        for(Map<String,String> actPropMap : actPropList){
            if(actPropMap.get("name").equals("first_name")) {
                String actFName = actPropMap.get("value");

                List<Map<String, String>> expPropList = createContactPojo.getProperties();
                for (Map<String, String> expPropMap : expPropList) {
                    if (expPropMap.get("name").equals("first_name")) {
                        String expFName = expPropMap.get("value");
                        Assert.assertEquals(expFName, actFName);
                    }
                }
            }
                else if (actPropMap.get("name").equals("last_name")){
                    String actLName = actPropMap.get("value");

                    List<Map<String,String>> expPropList = createContactPojo.getProperties();
                    for(Map<String,String> expPropMap : expPropList){
                        if(expPropMap.get("name").equals("last_name")){
                            String expLName =  expPropMap.get("value");
                            Assert.assertEquals(expLName,actLName);
                    }
                }
            }
                else if(actPropMap.get("name").equals("email")){
                    String actEmail = actPropMap.get("value");

                List<Map<String,String>> expPropList = createContactPojo.getProperties();
                for(Map<String,String> expPropMap : expPropList) {
                    if (expPropMap.get("name").equals("email")) {
                        String expEmail = expPropMap.get("value");

                        Assert.assertEquals(expEmail, actEmail);
                    }
                }
            }

        }
    }

    @When("I update the name and url of Contact")
    public void iUpdateTheNameAndUrlOfContact() {
        createContactPojo = new CreateContactPojo();

        createContactPojo.setId(createContactResponsePojo.getId());
        List<Map<String,String>> propList = new ArrayList<>();
        Map<String,String> propMap1 = new HashMap<>(Map.of("type","SYSTEM","name","first_name",
                "value",faker.name().firstName()));
        Map<String,String> propMap2 = new HashMap<>(Map.of("type","SYSTEM","name","last_name",
                "value",faker.name().lastName()));
        Map<String,String> propMap3 = new HashMap<>(Map.of("type","SYSTEM","name","email",
                "subtype","work","value",faker.company().url()));
        propList.add(propMap1);
        propList.add(propMap2);
        propList.add(propMap3);

        createContactPojo.setProperties(propList);

        specification= RestAssured.given();
        response = specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/contacts/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(createContactPojo)
                .log().all().put("edit-properties");

            response.prettyPrint();
            Assert.assertEquals(200,response.getStatusCode());


    }

    @Then("I verify response in particular Contact using DeSerialization")
    public void iVerifyResponseInParticularContactUsingDeSerialization() {

        specification= RestAssured.given();
        response = specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/contacts/")
                .pathParam("contactId",createContactPojo.getId())
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all().get("{contactId}");

        response.prettyPrint();
        Assert.assertEquals(200,response.getStatusCode());

        createContactResponsePojo = response.as(CreateContactResponsePojo.class);

           List<Map<String,String>> actPropList= createContactResponsePojo.getProperties();
        for(Map<String,String> actPropMap : actPropList) {
            if (actPropMap.get("name").equals("first_name")) {
                String actFName = actPropMap.get("value");

                List<Map<String, String>> expPropList = createContactPojo.getProperties();
                for (Map<String, String> expPropMap : expPropList) {
                    if (expPropMap.get("name").equals("first_name")) {
                        String expFName = expPropMap.get("value");
                        Assert.assertEquals(expFName, actFName);
                    }
                }
            } else if (actPropMap.get("name").equals("last_name")) {
                String actLName = actPropMap.get("value");

                List<Map<String, String>> expPropList = createContactPojo.getProperties();
                for (Map<String, String> expPropMap : expPropList) {
                    if (expPropMap.get("name").equals("last_name")) {
                        String expLName = expPropMap.get("value");
                        Assert.assertEquals(expLName, actLName);
                    }
                }
            } else if (actPropMap.get("name").equals("email")) {
                String actEmail = actPropMap.get("value");

                List<Map<String, String>> expPropList = createContactPojo.getProperties();
                for (Map<String, String> expPropMap : expPropList) {
                    if (expPropMap.get("name").equals("email")) {
                        String expEmail = expPropMap.get("value");

                        Assert.assertEquals(expEmail, actEmail);
                    }
                }
            }

        }
    }

    @And("I verify response in get all Contact using Deserialization")
    public void iVerifyResponseInGetAllContactUsingDeserialization() {

        specification= RestAssured.given();
        response = specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all().get("contacts");

        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

       CreateContactResponsePojo[] ResponsePojo = response.as(CreateContactResponsePojo[].class);

       for(CreateContactResponsePojo ContactObject: ResponsePojo){

          Long actId = ContactObject.getId();
          Long expId = createContactPojo.getId();

          if(actId==expId) {
              List<Map<String, String>> actPropList = ContactObject.getProperties();
              for (Map<String, String> actPropMap : actPropList) {
                  if (actPropMap.get("name").equals("first_name")) {
                      String actFName = actPropMap.get("value");

                      List<Map<String, String>> expPropList = createContactPojo.getProperties();
                      for (Map<String, String> expPropMap : expPropList) {
                          if (expPropMap.get("name").equals("first_name")) {
                              String expFName = expPropMap.get("value");
                              Assert.assertEquals(expFName, actFName);
                          }
                      }
                  } else if (actPropMap.get("name").equals("last_name")) {
                      String actLName = actPropMap.get("value");

                      List<Map<String, String>> expPropList = createContactPojo.getProperties();
                      for (Map<String, String> expPropMap : expPropList) {
                          if (expPropMap.get("name").equals("last_name")) {
                              String expLName = expPropMap.get("value");
                              Assert.assertEquals(expLName, actLName);
                          }
                      }
                  } else if (actPropMap.get("name").equals("email")) {
                      String actEmail = actPropMap.get("value");

                      List<Map<String, String>> expPropList = createContactPojo.getProperties();
                      for (Map<String, String> expPropMap : expPropList) {
                          if (expPropMap.get("name").equals("email")) {
                              String expEmail = expPropMap.get("value");

                              Assert.assertEquals(expEmail, actEmail);
                          }
                      }
                  }


              }
          }
       }

    }

    @When("I delete Contact")
    public void iDeleteContact() {

        specification= RestAssured.given();
        response = specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/contacts/")
                .pathParam("contactId",createContactPojo.getId())
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all().delete("{contactId}");

        response.prettyPrint();
        Assert.assertEquals(204,response.getStatusCode());


    }

    @Then("I verify delete Contact Successfully in particular contact Api")
    public void iVerifyDeleteContactSuccessfullyInParticularContactApi() {

        specification= RestAssured.given();
        response = specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/contacts/")
                .pathParam("contactId",createContactPojo.getId())
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all().delete("{contactId}");

        response.prettyPrint();
        Assert.assertEquals(204,response.getStatusCode());

    }

    @And("I verify Contact deleted successfully in get all Contact Api")
    public void iVerifyContactDeletedSuccessfullyInGetAllContactApi() {

        specification= RestAssured.given();
        response = specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all().get("contacts");

        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

        List<Long> idList = response.jsonPath().getList("id");
        System.out.println(idList);
        System.out.println(createContactPojo.getId());
        Assert.assertFalse(idList.contains(createContactPojo.getId()));

    }

    @Given("I setup a request structure to search contact dynamic search")
    public void iSetupARequestStructureToSearchContactDynamicSearch() {

        Map<String,String> param = new HashMap<>(Map.of("page_size","25","global_sort_key","created_time",
                "filterJson","{\"contact_type\":\"PERSON\"}"));

        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/filters/filter/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .contentType(ContentType.URLENC)
                .params(param)
                .log().all();
    }

    @When("I hit an Api to search Contact")
    public void iHitAnApiToSearchContact() {
        response = specification.post("dynamic-filter");
    }

    @Then("I verify Contacts are in sorted order in the response")
    public void iVerifyContactsAreInSortedOrderInTheResponse() {
        response.prettyPrint();

      Assert.assertEquals(200,response.getStatusCode());

      List<Integer> timeList = response.jsonPath().getList("created_time");

        System.out.println(timeList);
        List<Integer> expectedTimeList = timeList;

        Comparator<Integer> descendingComp = new Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };

        Collections.sort(expectedTimeList,Collections.reverseOrder(descendingComp));

        System.out.println("Desc Order :" + expectedTimeList);

        for(int i=0; i<expectedTimeList.size(); i++){
        //    System.out.println(expectedTimeList.get(i));
        //    System.out.println(timeList.get(i));
            Assert.assertEquals(expectedTimeList.get(i),timeList.get(i));
        }
    }
}
