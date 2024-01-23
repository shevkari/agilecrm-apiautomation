package com.agilecrm_automation.stepdefinition;

import com.agilecrm_automation.common.CommonFunctions;
import com.agilecrm_automation.pojo.CreateCompanyPojo;
import com.agilecrm_automation.pojo.CreateCompanyResponsePojo;
import com.github.javafaker.Faker;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CompanyStepDef {


    RequestSpecification specification;
    Response response;

    String compName;

    String compUrl;
    CreateCompanyPojo createCompanyPojo;
    CreateCompanyResponsePojo createCompanyResponsePojo;
    Long id;
    Faker faker = new Faker(new Random());

    @Given("I setup a request structure to get company information")
    public void setupRequestStructure(DataTable table) {

        RestAssured.config = RestAssured.config().sslConfig(SSLConfig.sslConfig().allowAllHostnames());
        specification = RestAssured.given();
        //.relaxedHTTPSValidation();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/contacts/companies/")
                .auth().basic("Rest@yopmail.com", "neie86ad56qv3ku0mn9o16ogg5")
                .header("Accept", "application/json")
                .header("Content-Type", "Application/json")
                .log().all();


    }

    @When("I hit an api to get all company info")
    public void hitAnApi() {
        response = specification.post("list");
    }

    @Then("I verify company response")
    public void iVerifyCompanyResponse() {
        response.prettyPrint();
        System.out.println(response.getStatusCode());

        // Verify Status Code
        int statusCode = response.getStatusCode();
        if (statusCode == 200) {
            System.out.println("Status code is valid");
        } else {
            System.out.println("Invalid status code");
        }

        List<Object> listobj = response.jsonPath().getList("id");
        System.out.println(listobj);

        listobj.forEach(x -> {
            System.out.println(x);
        });


        // get the response in the form of list<String>
        List<String> typelist = response.jsonPath().getList("type");
        System.out.println(typelist);

        typelist.forEach(x ->
        {
            System.out.println(x);
            if (x.equals("COMPANY")) {
                System.out.println("Verify type is Company");
            }
        });

        // get the response in the form of list<Object>
        List<Object> listobj1 = response.jsonPath().getList("");
        for (Object obj : listobj1) {
            Map<String, Object> mapobj = (Map<String, Object>) obj;

            String name = mapobj.get("type").toString();

            // get the each company

            System.out.println(name);


            List<Long> lobj = response.jsonPath().get("id");
            lobj.forEach(x -> {
                System.out.println(x);
            });

        }


        List<Object> tobj = response.jsonPath().getList("tags");
        System.out.println(tobj);
        for (Object obj2 : tobj) {
            System.out.println(obj2);
            List<String> str = (List<String>) obj2;
            for (String s : str) {
                System.out.println(s);
            }
        }


        List<Integer> starlist = response.jsonPath().getList("star_value");
        starlist.forEach(x -> {
            if (x >= 4) {
                //   System.out.println(x);
                List<Object> propobj = response.jsonPath().get("properties");
                //  System.out.println(propobj);
                for (Object obj : propobj) {
                    //  System.out.println(obj);
                    List<Object> list = (List<Object>) obj;
                    for (Object objlist : list) {
                        //System.out.println(objlist);
                        Map<String, String> li = (Map<String, String>) objlist;
                        li.forEach((y, z) ->
                        {
                            System.out.println(y + ":" + z);
                        });
                    }

                }
            }
        });

    }

    @When("I get name of all companies")
    public void getAllCompanyName()
        {
            List<Object> compList = response.jsonPath().getList("");
            for(Object compObj: compList)
            {
                Map<String,Object> mapobj = (Map<String,Object>)compObj;
              Object obj =  mapobj.get("properties");
                List<Object> lobj = (List<Object>)obj;
                for(Object mobj : lobj)
                {
                    Map<String,String> mstring = (Map<String,String>)mobj;
                   if(mstring.get("name").equals("name"))
                   {
                      String compName =  mstring.get("value");
                      String id = mapobj.get("id").toString();

                       System.out.println(id + " : " + compName);
                   }
                }
            }
            //List<Object> compNameList = response.jsonPath().get("properties.Value");
            //System.out.println(compNameList);
        }
        @When("I get name of Companies which has tag is not empty")
        public void nonEmptyTagCompanyName() {
            List<Object> list = response.jsonPath().getList("");
            for (Object obj : list) {
                Map<String, Object> compMap = (Map<String, Object>) obj;

                Object tagobj = compMap.get("tags");

                List<String> taglist = (List<String>) tagobj;
                if (!taglist.isEmpty()) {
                    Object object = compMap.get("properties");
                    List<Object> listobj = (List<Object>) object;
                    for (Object proObj : listobj) {
                        Map<String, String> propMap = (Map<String, String>) proObj;
                        String name = propMap.get("name");

                        if (name.equals("name")) {
                            String compName = propMap.get("value");
                            //  System.out.println(compName);
                        }
                    }
                }
            }

            // get company name & Url
            List<Object> compName = response.jsonPath().getList("");
            for (Object obj1 : compName) {

                Map<String, Object> obj2 = (Map<String, Object>) obj1;

                Object obj3 = obj2.get("properties");
                List<Object> obj4 = (List<Object>) obj3;
                for (Object obj5 : obj4) {
                    Map<String, String> obj6 = (Map<String, String>) obj5;
                    String compname = obj6.get("name");


                    if ((compname.equals("name")) || (compname.equals("url"))) {
                        String nameUrl = obj6.get("value");
                        //  System.out.println(nameUrl);

                    }
//                    String co="",co1="";
//                    if(compname.equals("name"))
//                    {
//                        co= obj6.get("value");
//                    }
//                    else if(compname.equals("url")){
//                         co1= obj6.get("value");
//                    }
//                    else{continue;
//                        }
//                    System.out.println(co +" : "+ co1);
                }
            }

            List<Long> id= response.jsonPath().getList("owner.id");
            for(Long l : id){
                System.out.println(l);
            }
        }

    @Given("I setup a request structure to get company info with below keyword")
    public void iSetupARequestStructureToGetCompanyInfo(Map<String,String> queryParam) {
        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/search")
                .queryParams(queryParam)
                .auth().basic("Rest@yopmail.com", "neie86ad56qv3ku0mn9o16ogg5")
                .header("Accept","Application/json")
                .log().all();

    }

    @When("I hit an api to search company with keywords")
    public void iHitAnApiToSearchCompanyWithKeywords() {
        response = specification.get();
    }

    @Then("I get result according to keywords")
    public void iGetResultAccordingToKeywords() {
        response.prettyPrint();

        List<Object> propList = response.jsonPath().getList("");
        int size = propList.size();

        Assert.assertTrue(size<=10);

        List<String> type = response.jsonPath().getList("type");
        for(String actType : type ) {
            String exptType = "COMPANY";
            Assert.assertEquals(exptType, actType);
        }

        Object objList = response.jsonPath().getList("properties");

            List<Object> listObj = (List<Object>) objList;

        for(Object list : listObj) {

            List<Object> ll = (List<Object>) list;
            for (Object ll1 : ll) {

                Map<String, String> map = (Map<String, String>) ll1;
               String val = map.get("value");

               boolean result = val.contains("spicejet");

               if(result==true) {
                   String actVal =map.get("value");
                   String expVal = "spicejet";
                   Assert.assertTrue(expVal,actVal.contains("spicejet"));
               }
            }
        }
    }

    @Given("I setup a request structure to create company with following info")
    public void iSetupARequestStructureToCreateCompanyWithFollowingInfo(Map<String,String> mapdata) {



        compName = mapdata.get("compName");

        compUrl = mapdata.get("url");

        String reqBody ="{\n" +
                "    \"type\": \"COMPANY\",\n" +
                "    \"properties\": [\n" +
                "        {\n" +
                "            \"name\": \"Company Type\",\n" +
                "            \"type\": \"CUSTOM\",\n" +
                "            \"value\": \"MNC Inc\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"SYSTEM\",\n" +
                "            \"name\": \"name\",\n" +
                "            \"value\": \""+compName+"\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"SYSTEM\",\n" +
                "            \"name\": \"url\",\n" +
                "            \"value\": \""+compUrl+"\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"phone\",\n" +
                "            \"value\": \"45500000\",\n" +
                "            \"subtype\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"website\",\n" +
                "            \"value\": \"http://www.linkedin.com/company/agile-crm\",\n" +
                "            \"subtype\": \"LINKEDIN\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"address\",\n" +
                "            \"value\": \"{\\\"address\\\":\\\"MS 35, 440 N Wolfe Road\\\",\\\"city\\\":\\\"Sunnyvale\\\",\\\"state\\\":\\\"CA\\\",\\\"zip\\\":\\\"94085\\\",\\\"country\\\":\\\"US\\\"}\",\n" +
                "            \"subtype\": \"office\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api")
                .body(reqBody)
                .auth().basic("Rest@yopmail.com", "neie86ad56qv3ku0mn9o16ogg5")
           //     .header("Accept","Application/json")
           //     .header("content-type","application/json")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all();
    }

    @When("I hit a create company api")
    public void CreateCompany() {
        response = specification.post("/contacts");
    }

    @Then("I verify company created successfully")
    public void iVerifyCompanyCreatedSuccessfully() {
        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

       String compType = response.jsonPath().get("type");

       Assert.assertEquals("COMPANY",compType);


       List<Map<String,String>> mapData = response.jsonPath().getList("properties");

       for(Map<String,String> propObj : mapData){
       if(propObj.get("name").equals("name")) {
           String actName = propObj.get("value");
            Assert.assertEquals(compName,actName);
       }else if(propObj.get("name").equals("url")){
           String actUrl = propObj.get("value");
           Assert.assertEquals(compUrl,actUrl);
       }



       }

    }

    @Given("I setup a request structure to create company with hashmap object")
    public void iSetupARequestStructureToCreateCompanyWithHashmapObject(Map<String,String> map) {

       compName = map.get("compName");
       compUrl = map.get("url");

       String reqBody = "{\n" +
               "    \"type\": \"COMPANY\",\n" +
               "    \"properties\": [\n" +
               "        {\n" +
               "            \"name\": \"Company Type\",\n" +
               "            \"type\": \"CUSTOM\",\n" +
               "            \"value\": \"MNC Inc\"\n" +
               "        },\n" +
               "        {\n" +
               "            \"type\": \"SYSTEM\",\n" +
               "            \"name\": \"name\",\n" +
               "            \"value\": \""+compName+"\"\n" +
               "        },\n" +
               "        {\n" +
               "            \"type\": \"SYSTEM\",\n" +
               "            \"name\": \"url\",\n" +
               "            \"value\": \""+compUrl+"\"\n" +
               "        },\n" +
               "        {\n" +
               "            \"name\": \"phone\",\n" +
               "            \"value\": \"45500000\",\n" +
               "            \"subtype\": \"\"\n" +
               "        },\n" +
               "        {\n" +
               "            \"name\": \"website\",\n" +
               "            \"value\": \"http://www.linkedin.com/company/agile-crm\",\n" +
               "            \"subtype\": \"LINKEDIN\"\n" +
               "        },\n" +
               "        {\n" +
               "            \"name\": \"address\",\n" +
               "            \"value\": \"{\\\"address\\\":\\\"MS 35, 440 N Wolfe Road\\\",\\\"city\\\":\\\"Sunnyvale\\\",\\\"state\\\":\\\"CA\\\",\\\"zip\\\":\\\"94085\\\",\\\"country\\\":\\\"US\\\"}\",\n" +
               "            \"subtype\": \"office\"\n" +
               "        }\n" +
               "    ]\n" +
               "}";


                specification = RestAssured.given();
                specification.baseUri("https://restapi.agilecrm.com/")
                        .basePath("dev/api")
                        .body(reqBody)
                        .auth().basic("Rest@yopmail.com", "neie86ad56qv3ku0mn9o16ogg5")
                        .accept(ContentType.JSON)
                        .contentType(ContentType.JSON)
                        .log().all();
    }

    @Then("I verify company created successfully with {int} status code")
    public void iVerifyCompanyCreatedSuccessfullyWithStatusCode(int statusCode) {
        response.prettyPrint();

        Assert. assertEquals(statusCode,response.getStatusCode());

        List<Map<String,String>> resPropList = response.jsonPath().get("properties");

        for(Map<String,String> resPropMap : resPropList) {
            if (resPropMap.get("name").equals("name")) {
                String actName = resPropMap.get("value");
                Assert.assertEquals(compName, actName);
            } else if (resPropMap.get("name").equals("url")) {
                String actUrl = resPropMap.get("value");
                Assert.assertEquals(actUrl, compUrl);
            }
        }


    }

    @Given("I setup a request structure to create company with hasmap object")
    public void iSetupARequestStructureToCreateCompanyWithHasmapObject(DataTable table) {
       List<Map<String,String>> listMap =  table.asMaps();
       Map<String,String> data = listMap.get(0);
        //System.out.println(data);
        compName = data.get("compName");
        compUrl = data.get("url");

       // System.out.println(compName+":"+compUrl);

        Map<String,Object> reqbody = new HashMap<>();
        reqbody.put("type","COMPANY");

        List<Map<String,String>> propList = new ArrayList<>();

        Map<String,String> map1 = new HashMap<>();

        map1.put("type","Company Type");
        map1.put("type","Custom");
        map1.put("value","MNC Inc");

        propList.add(map1);

        Map<String,String> map2 = new HashMap<>();

        map2.put("type","SYSTEM");
        map2.put("name","name");


        Map<String,String> map3 = new HashMap<>();

        map3.put("type","SYSTEM");
        map3.put("name","url");

        if(compName==null){
            map2.put("value",null);
            map3.put("value",compUrl);
        }
        else if(compUrl==null){
            map2.put("value",compName);
            map3.put("value",null);
        }
        else if(compName==null && compUrl==null){
            map2.put("value",null);
            map3.put("value",null);
        }
        else {
            map2.put("value",compName);
            map2.put("value",compUrl);
        }

        propList.add(map2);
        propList.add(map3);

        reqbody.put("properties",propList);

        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api")
                .auth().basic("Rest@yopmail.com", "neie86ad56qv3ku0mn9o16ogg5")
              //  .header("Accept",ContentType.JSON)
              //  .header("Content-Type",ContentType.JSON)
                .body(reqbody)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all();

    }


    @Then("I verify company created successfully with {int} code")
    public void iVerifyCompanyCreatedSuccessfullyWithStatusCodeCode(int statusCode) {
        response.prettyPrint();

        Assert.assertEquals(statusCode,response.getStatusCode());


        List<Map<String,String>> resPropList = response.jsonPath().get("properties");

        for(Map<String,String> resPropMap : resPropList){
            if(resPropMap.get("name").equals("name")){
                String actName = resPropMap.get("value");
                Assert.assertEquals(compName,actName);
            }
            else if(resPropMap.get("name").equals("url")){
                String actUrl = resPropMap.get("value");
                Assert.assertEquals(actUrl,compUrl);
            }
        }

    }

    @Given("I setup a request structure to create company with json file")
    public void iSetupARequestStructureToCreateCompanyWithJsonFile() throws IOException, ParseException {

        String filePath = System.getProperty("user.dir")+"/src/test/resources/RequestFiles/CreateCompanyRequest.json";

        File file = new File(filePath);

        FileReader fileReader = new FileReader(file);

        JSONParser jsonparser = new JSONParser();

        Object inputObject = jsonparser.parse(fileReader);

        JSONObject jsonObject = (JSONObject)inputObject;

        JSONArray jsonarray = (JSONArray)jsonObject.get("properties");

        JSONObject nameObject =(JSONObject)jsonarray.get(1);

        nameObject.put("value","Facebook");
        compName = nameObject.get("value").toString();

        JSONObject urlObject = (JSONObject)jsonarray.get(2);
        urlObject.put("value","www.facebook.com");
        compUrl  = urlObject.get("value").toString();

        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
            //    .body(file)
                .body(jsonObject)
                .log().all();
    }

    @Given("I setup a request structure to create company")
    public void iSetupARequestStructureToCreateCompany() {
        createCompanyPojo = new CreateCompanyPojo();
        createCompanyPojo.setType("COMPANY");
        createCompanyPojo.setStar_value(9);
        createCompanyPojo.setLead_score(128);

      //  String[] tags = {"Permanent","India"};
        List<String> tags = new ArrayList<>();
        tags.add("Permanent");
        tags.add("India");
        createCompanyPojo.setTags(tags);

        List<Map<String,String>> propList = new ArrayList<>();
        Map<String,String> map1 = new HashMap<>();
        map1.put("name", "Company Type");
        map1.put("type", "CUSTOM");
        map1.put("value", "MNC Inc");


        Map<String,String> map2 = new HashMap<>();
        map2.put("type","SYSTEM");
        map2.put( "name","name");
        map2.put("value", "Microsoft");

        Map<String,String> map3 = new HashMap<>();
        map3.put("type", "SYSTEM");
        map3.put(  "name", "url");
        map3.put("value", "http://www.microsoft.com/");

//        Map<String,String> map4 = new HashMap<>();
//        map4.put("name", "phone");
//        map4.put("value", "45500065794");
//        map4.put("subtype", "IT");

//        Map<String,String> map5 = new HashMap<>();
//        map5.put("name","website");
//        map5.put("value","http://www.microsoft.com/company/agile-crm");
//        map5.put("subtype", "LINKEDIN");

        propList.add(map1);
        propList.add(map2);
        propList.add(map3);
//        propList.add(map4);
//        propList.add(map5);

        createCompanyPojo.setProperties(propList);

        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(createCompanyPojo)
                .log().all();

    }

    @When("I hit an api to create company")
    public void iHitAnApiToCreateCompany() {
        response=specification.post("contacts");
    }

    @Then("I verify Company Created successfully")
    public void iVerifyCompanyCreated() {
        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

        List<Map<String,String>> propList = response.jsonPath().getList("properties");
        for(Map<String,String> propMap : propList){
            String actName = propMap.get("name");
            String actValue = propMap.get("value");

            List<Map<String,String>> expPropList = createCompanyPojo.getProperties();
            for(Map<String,String> expPropMap : expPropList){

                if(actName.equals(expPropMap.get("name"))){
                    Assert.assertEquals(expPropMap.get("value"),actValue);
                }
            }

        }
    }


    @Then("I verify Company response using Deserialization successfully")
    public void iVerifyCompanyResponseUsingDeserializationSuccessfully() {
        response.prettyPrint();
        Assert.assertEquals(200,response.getStatusCode());

        createCompanyResponsePojo =response.as(CreateCompanyResponsePojo.class);
       int starVal = createCompanyResponsePojo.getStar_value();
        System.out.println(starVal);
        Assert.assertEquals(createCompanyPojo.getStar_value(),starVal);

        int leadScore = createCompanyResponsePojo.getLead_score();
        Assert.assertEquals(createCompanyPojo.getLead_score(),leadScore);

    }

    @Then("I verify Company Created successfully using Deserialization")
    public void iVerifyCompanyCreatedSuccessfullyUsingDeserialization() {
        response.prettyPrint();
        Assert.assertEquals(200,response.getStatusCode());

        createCompanyResponsePojo =response.as(CreateCompanyResponsePojo.class);

        List<Map<String,String>> propList = createCompanyResponsePojo.getProperties();

        for(Map<String,String> propMap : propList){
            String actName = propMap.get("name");
            String actValue = propMap.get("value");

           List<Map<String,String>> expPropList = createCompanyPojo.getProperties();
           for(Map<String,String> expPropMap : expPropList){
               String expName = expPropMap.get("name");
               String expValue = expPropMap.get("value");
               if(actName.equals(expName)){
                   Assert.assertEquals(expValue,actValue);
               }
           }

        }

        String actType = createCompanyResponsePojo.getType();
        String expType = createCompanyPojo.getType();

        Assert.assertEquals(expType,actType);

        List<String> actTags = createCompanyResponsePojo.getTags();
        List<String> expTags = createCompanyPojo.getTags();

        Assert.assertEquals(expTags,actTags);

        int actStarVal = createCompanyResponsePojo.getStar_value();
        int expStarVal = createCompanyPojo.getStar_value();

        Assert.assertEquals(expStarVal,actStarVal);

        int actLeadScore = createCompanyResponsePojo.getLead_score();
        int expLeadScore = createCompanyPojo.getLead_score();

        Assert.assertEquals(expLeadScore,actLeadScore);

         id = createCompanyResponsePojo.getId();

    }

    @Then("I Verify Particular company in response by DeSerialization")
    public void iVerifyParticularCompanyInResponseByDeSerialization() {

        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParam("contactid",id)
                .log().all();


        response=specification.get("contacts/{contactid}");

        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

        createCompanyResponsePojo = response.as(CreateCompanyResponsePojo.class);

        Long actId = createCompanyResponsePojo.getId();
//        System.out.println(actId);
//        System.out.println(id);

        Assert.assertEquals(id,actId);

    }

    @Then("I verify particular company in get all company Api")
    public void iVerifyParticularCompanyInGetAllCompanyApi() {
        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/contacts/companies")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all();

        response = specification.post("/list");
        response.prettyPrint();
        Assert.assertEquals(200,response.getStatusCode());

        CreateCompanyResponsePojo[] resList= response.as(CreateCompanyResponsePojo[].class);


            for(CreateCompanyResponsePojo CCRP :resList){


                if(CCRP.getId().equals(id)) {
                    List<Map<String, String>> propList = CCRP.getProperties();
                    for (Map<String, String> propMap : propList) {
                        String actName = propMap.get("name");
                        String actValue = propMap.get("value");
                        List<Map<String, String>> expPropList = createCompanyPojo.getProperties();
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

    @Given("I setup a request to create company using request body using Serialization")
    public void iSetupARequestToCreateCompanyUsingRequestBodyUsingSerialization() {
        createCompanyPojo = new CreateCompanyPojo();
        createCompanyPojo.setType("COMPANY");
        createCompanyPojo.setStar_value(5);
        createCompanyPojo.setLead_score(100);
        List<String> tags = new ArrayList<>(List.of("Permanent","India","Pune"));
        createCompanyPojo.setTags(tags);
        List<Map<String,String>> propList = new ArrayList<>();
        Map<String,String> propMap1 = new HashMap<>(Map.of("name","CompanyType","type","CUSTOM",
                                                "value","MNC Inc"));
        Map<String,String> propMap2 = new HashMap<>(Map.of("type","SYSTEM","name","name",
                                                "value",faker.company().name()));
        Map<String,String> propMap3 = new HashMap<>(Map.of("type","SYSTEM","name","url",
                "value",faker.company().url()));

        propList.add(propMap1);
        propList.add(propMap2);
        propList.add(propMap3);

        createCompanyPojo.setProperties(propList);

        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(createCompanyPojo)
                .log().all();
    }

    @When("I hit an api to create")
    public void iHitAnApiToCreate() {
        response = specification.post("contacts");

    }

    @Then("I verify created company in the response using DeSerialization")
    public void iVerifyCreatedCompanyInTheResponseUsingDeSerialization() {
        response.prettyPrint();
        Assert.assertEquals(200,response.getStatusCode());

        createCompanyResponsePojo = response.as(CreateCompanyResponsePojo.class);
        List<Map<String,String>> propList = createCompanyResponsePojo.getProperties();
        for(Map<String,String> mapObject : propList){
            if(mapObject.get("name").equals("name")){
                String actName = mapObject.get("value");

                List<Map<String,String>> expPropList =createCompanyPojo.getProperties();
                for(Map<String,String> expMapObj : expPropList){
                    if(expMapObj.get("name").equals("name")){
                        String expName = expMapObj.get("value");

                        Assert.assertEquals(expName,actName);
                    }
                }
            }
            else if(mapObject.get("name").equals("url")){
                String actUrl = mapObject.get("value");
                List<Map<String,String>> expPropList =createCompanyPojo.getProperties();
                for(Map<String,String> expMapObj : expPropList) {
                    if (expMapObj.get("name").equals("url")) {
                        String expUrl = expMapObj.get("value");

                        Assert.assertEquals(expUrl, actUrl);
                    }
                }
            }
        }

    }

    @When("I update the name and url of company")
    public void iUpdateTheNameAndUrlOfCompany() {
        createCompanyPojo = new CreateCompanyPojo();

        List<Map<String,String>> propList = new ArrayList<>();

        Map<String,String> propMap1 = new HashMap<>(Map.of("type","SYSTEM","name","name",
                "value",faker.company().name()));
        Map<String,String> propMap2 = new HashMap<>(Map.of("type","SYSTEM","name","url",
                "value",faker.company().url()));
        propList.add(propMap1);
        propList.add(propMap2);

        createCompanyPojo.setProperties(propList);
        Long contactId = createCompanyResponsePojo.getId();
        createCompanyPojo.setId(contactId);
        specification = RestAssured.given();
        response = specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/contacts/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(createCompanyPojo)
                .log().all().put("edit-properties");

    }

    @Then("I verify response in particular Company using DeSerialization")
    public void iVerifyResponseInParticularCompanyUsingDeSerialization() {
        response.prettyPrint();
        Assert.assertEquals(200,response.getStatusCode());

        createCompanyResponsePojo = response.as(CreateCompanyResponsePojo.class);
        Long actId = createCompanyResponsePojo.getId();
        Long expId = createCompanyPojo.getId();
        Assert.assertEquals(actId,expId);

        List<Map<String,String>> updatedPropList = createCompanyResponsePojo.getProperties();
        for(Map<String,String> updatedPropMap :updatedPropList ){
            if(updatedPropMap.get("name").equals("name")){
                String updatedName = updatedPropMap.get("value");

                List<Map<String,String>> providedPropList=createCompanyPojo.getProperties();
                for(Map<String,String> providedPropMap:providedPropList) {
                    if (providedPropMap.get("name").equals("name")) {
                        String providedName = providedPropMap.get("value");

                        Assert.assertEquals(providedName, updatedName);
                    }
                }
            }
            else if(updatedPropMap.get("name").equals("url")){
                String updatedName = updatedPropMap.get("value");

                List<Map<String,String>> providedPropList=createCompanyPojo.getProperties();
                for(Map<String,String> providedPropMap:providedPropList) {
                    if (providedPropMap.get("name").equals("url")) {
                        String providedName = providedPropMap.get("value");

                        Assert.assertEquals(providedName, updatedName);
                    }
                }
            }
        }
    }

    @And("I verify response in get all company using Deserialization")
    public void iVerifyResponseInGetAllCompanyUsingDeserialization() {

        specification = RestAssured.given();
        response = specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/contacts/companies/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all().post("list");

        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

//        List<Long> idList = response.jsonPath().getList("id");
//        if(idList.contains(createCompanyPojo.getId())){
//            System.out.println(idList);
//            System.out.println(createCompanyPojo.getId());
//        }

        CreateCompanyResponsePojo[] createCompResp = response.as(CreateCompanyResponsePojo[].class);
        for(CreateCompanyResponsePojo compresp : createCompResp){



            if(compresp.getId().equals(createCompanyPojo.getId())){

                List<Map<String,String>> propList =  compresp.getProperties();
                for(Map<String,String> propMap: propList){
                  if(propMap.get("name").equals("name")){
                        String updatedName = propMap.get("value");

                        List<Map<String,String>> providedPropList=createCompanyPojo.getProperties();
                        for(Map<String,String> providedPropMap:providedPropList) {
                            if (providedPropMap.get("name").equals("name")) {
                                String providedName = providedPropMap.get("value");

                                Assert.assertEquals(providedName, updatedName);
                            }
                        }
                    }
                    else if(propMap.get("name").equals("url")) {
                      String updatedName = propMap.get("value");

                      List<Map<String, String>> providedPropList = createCompanyPojo.getProperties();
                      for (Map<String, String> providedPropMap : providedPropList) {
                          if (providedPropMap.get("name").equals("url")) {
                              String providedName = providedPropMap.get("value");
                              System.out.println("Successfully Verified that company updated...");
                              Assert.assertEquals(providedName, updatedName);
                          }
                        }
                      }
                  }
            }
        }

    }

    @When("I delete Company")
    public void iDeleteCompany() {

        specification = RestAssured.given();
        response = specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/contacts/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParam("contcatId",createCompanyPojo.getId())
                .log().all().delete("{contcatId}");

        response.prettyPrint();

        Assert.assertEquals(204,response.getStatusCode());
    }

    @Then("I verify delete Company Successfully in particular company Api")
    public void iVerifyDeleteCompanySuccessfullyInParticularCompany() {

        specification = RestAssured.given();
        response = specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/contacts/")
                .pathParam("ContactId",createCompanyPojo.getId())
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all().get("{ContactId}");

        response.prettyPrint();


        Assert.assertEquals(204,response.getStatusCode());



    }

    @And("I verify company deleted successfully in get all Company Api")
    public void iVerifyCompanyDeletedSuccessfullyInGetAllCompanyApi() {

        specification = RestAssured.given();
        response = specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/contacts/companies/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all().post("list");

        response.prettyPrint();
        Assert.assertEquals(200,response.getStatusCode());

        List<Long> idList = response.jsonPath().getList("id");

        Assert.assertFalse(idList.equals(createCompanyPojo.getId()));
    }

    @Given("I create company with file body")
    public void iCreateCompanyWithFileBody() throws IOException {

        String filePath = System.getProperty("user.dir")+"/src/test/resources/RequestFiles/CreateCompanyRequest.json";
        File file = new File(filePath);
        CommonFunctions commonFunctions = new CommonFunctions();
        response = commonFunctions.postWithRequestBody(file,"company");

    }

    @Then("I verify company created in response")
    public void iVerifyCompanyCreatedInResponse() {
        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());


    }

    @Given("I create company with String body")
    public void iCreateCompanyWithStringBody() throws IOException {
        String reqBody = "{\n" +
                "    \"type\": \"COMPANY\",\n" +
                "    \"star_value\": 4,\n" +
                "    \"lead_score\": 120,\n" +
                "    \"tags\": [\n" +
                "        \"Permanent\",\n" +
                "        \"USA\",\n" +
                "        \"Hongkong\",\n" +
                "        \"Japan\"\n" +
                "    ],\n" +
                "    \"properties\": [\n" +
                "        {\n" +
                "            \"name\": \"Company Type\",\n" +
                "            \"type\": \"CUSTOM\",\n" +
                "            \"value\": \"MNC Inc\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"SYSTEM\",\n" +
                "            \"name\": \"name\",\n" +
                "            \"value\": \"Spicejet\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"SYSTEM\",\n" +
                "            \"name\": \"url\",\n" +
                "            \"value\": \"http://www.spicejet.com/\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"phone\",\n" +
                "            \"value\": \"45500000\",\n" +
                "            \"subtype\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"website\",\n" +
                "            \"value\": \"http://www.linkedin.com/company/agile-crm\",\n" +
                "            \"subtype\": \"LINKEDIN\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"address\",\n" +
                "            \"value\": \"{\\\"address\\\":\\\"MS 35, 440 N Wolfe Road\\\",\\\"city\\\":\\\"Sunnyvale\\\",\\\"state\\\":\\\"CA\\\",\\\"zip\\\":\\\"94085\\\",\\\"country\\\":\\\"US\\\"}\",\n" +
                "            \"subtype\": \"office\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        CommonFunctions commonFunctions = new CommonFunctions();
        response=commonFunctions.postWithRequestBody(reqBody,"company");
    }

    @Then("I verify company created Successfully in response")
    public void iVerifyCompanyCreatedSuccessfullyInResponse() {
        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

    }

    @Given("I create company with Pojo body")
    public void iCreateCompanyWithPojoBody() throws IOException {
        createCompanyPojo = new CreateCompanyPojo();
        createCompanyPojo.setType("COMPANY");
        List<Map<String,String>> list = new ArrayList<>();
        Map<String,String> propMap1 = new HashMap<>(Map.of("name","CompanyType","type","CUSTOM",
                "value","MNC Inc"));
        Map<String,String> propMap2 = new HashMap<>(Map.of("type","SYSTEM","name","name",
                "value",faker.company().name()));
        Map<String,String> propMap3 = new HashMap<>(Map.of("type","SYSTEM","name","url",
                "value",faker.company().url()));
        list.add(propMap1);
        list.add(propMap2);
        list.add(propMap3);
        createCompanyPojo.setProperties(list);

        CommonFunctions commonFunctions = new CommonFunctions();
        response = commonFunctions.postWithRequestBody(createCompanyPojo,"company");

    }


    @Then("I verify company Retrieve Successfully in response")
    public void iVerifyCompanyRetrieveSuccessfullyInResponse() throws IOException {

        CommonFunctions commonFunctions = new CommonFunctions();
        Long expId = response.jsonPath().getLong("id");

        response = commonFunctions.getResponseWithPathParams(expId.toString(),"company");
        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

        Long actId = response.jsonPath().getLong("id");
        Assert.assertEquals(expId,actId);

    }


    @Given("I update company with String body")
    public void iUpdateCompanyWithStringBody() throws IOException {

        String reqBody = "{\n" +
                "    \"id\": 4523050643816448,\n" +
                "    \"properties\": [\n" +
                "        {\n" +
                "            \"type\": \"SYSTEM\",\n" +
                "            \"name\": \"name\",\n" +
                "            \"value\": \"AMAZON\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"SYSTEM\",\n" +
                "            \"name\": \"url\",\n" +
                "            \"value\": \"http://www.amazon.com/\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"phone\",\n" +
                "            \"value\": \"45500000\",\n" +
                "            \"subtype\": \"\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        CommonFunctions commonFunctions = new CommonFunctions();
        response = commonFunctions.putWithRequestBody(reqBody,"updateContact");


    }

    @Then("I verify company updated Successfully in response")
    public void iVerifyCompanyUpdatedSuccessfullyInResponse() {
        response.prettyPrint();
        System.out.println(response.getStatusCode());
    }

    @Given("I Delete company")
    public void iDeleteCOmpany() throws IOException {

        CommonFunctions commonFunctions = new CommonFunctions();
        response = commonFunctions.deleteWithPathParams("4523050643816448","deleteCompany");
    }

    @Then("I verify company deleted Successfully in response")
    public void iVerifyCompanyDeletedSuccessfullyInResponse() {
        response.prettyPrint();
        System.out.println(response.getStatusCode());
    }

    @Given("I update company with Pojo Class request body")
    public void iUpdateCompanyWithPojoClassRequestBody() throws IOException {
        createCompanyPojo = new CreateCompanyPojo();
        createCompanyPojo.setId(4523059074367488l);
        List<Map<String,String>> list = new ArrayList<>();
        Map<String,String> propMap2 = new HashMap<>(Map.of("type","SYSTEM","name","name",
                "value",faker.company().name()));
        Map<String,String> propMap3 = new HashMap<>(Map.of("type","SYSTEM","name","url",
                "value",faker.company().url()));
        list.add(propMap2);
        list.add(propMap3);
        createCompanyPojo.setProperties(list);

        CommonFunctions commonFunctions = new CommonFunctions();
        response=commonFunctions.putWithRequestBody(createCompanyPojo,"updateContact");
    }



}
