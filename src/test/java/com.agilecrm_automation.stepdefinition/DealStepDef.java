package com.agilecrm_automation.stepdefinition;

import com.agilecrm_automation.pojo.CreateDealPojo;
import com.agilecrm_automation.pojo.CreateDealResponsePojo;
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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DealStepDef {

    RequestSpecification specification;
    Response response;

    String dealName;
    String expectedValue;

    String mileStone;
    CreateDealPojo createDealPojo;
    CreateDealResponsePojo createDealResponsePojo;

    Faker faker = new Faker(new Random());

    @Given("I setup a request structure to create Deal with following info")
    public void iSetupARequestStructureToCreateDealWithFollowingInfo(Map<String,String> dataMap) {

        dealName = dataMap.get("name");
        expectedValue = dataMap.get("expected_value");


        String reqBody = "{\n" +
                "    \"name\": \""+dealName+"\",\n" +
                "    \"expected_value\": \""+expectedValue+"\",\n" +
                "    \"probability\": \"75\",\n" +
                "    \"close_date\": 1455042600,\n" +
                "    \"milestone\": \"Proposal\",\n" +
                "    \"contact_ids\": [\n" +
                "        \"5694691248963584\"\n" +
                "    ],\n" +
                "    \"custom_data\": [\n" +
                "        {\n" +
                "            \"name\": \"Group Size\",\n" +
                "            \"value\": \"10\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";


        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(reqBody)
                .log().all();
    }

    @When("I hit an api to create Deal")
    public void iHitAnApiToCreateDeal() {
        response=specification.post("opportunity");
    }

    @Then("I verify with Deal created Successfully")
    public void iVerifyWithDealCreatedSuccessfully() {
        response.prettyPrint();
        Assert.assertEquals(200,response.getStatusCode());

        String actName = response.jsonPath().get("name");
        Assert.assertEquals(actName,dealName);

      //  String actMilestone = response.jsonPath().get("expected_value").toString();
      //  Assert.assertEquals(actMilestone,expectedValue);

    }

    @Given("I setup a request structure to create Deal with following info in hashMap")
    public void iSetupARequestStructureToCreateDealWithFollowingInfoInHashMap(DataTable table) {
        List<Map<String,String>> dataList = table.asMaps();

        Map<String,String> dataMap = dataList.get(0);
        dealName = dataMap.get("name");
        expectedValue = dataMap.get("expected_value");
         mileStone = dataMap.get("milestone");
        System.out.println(expectedValue);

        Map<String,String> map1 = new HashMap<>();

        if(dealName==null){
            map1.put("name",null);
            map1.put("expected_value",expectedValue);
            map1.put("milestone",mileStone);
        }else if(expectedValue==null){
            map1.put("name",dealName);
            map1.put("expected_value",null);
            map1.put("milestone",mileStone);
        }else if(mileStone==null){
            map1.put("name",dealName);
            map1.put("expected_value",expectedValue);
            map1.put("milestone",null);
        }
        else if((dealName==null && expectedValue==null) || (dealName==null && mileStone==null) || (mileStone==null && expectedValue==null)){
            map1.put("name",dealName);
            map1.put("expected_value",expectedValue);
            map1.put("milestone",mileStone);
        }else{
            map1.put("name",dealName);
            map1.put("expected_value",expectedValue);
            map1.put("milestone",mileStone);
        }

        specification=RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(map1)
                .log().all();



    }

    @Then("I verify with Deal created Successfully with {int} status code")
    public void iVerifyWithDealCreatedSuccessfullyWithStatuscodeStatusCode(int statuscode) {
        response.prettyPrint();
        Assert.assertEquals(statuscode,response.getStatusCode());

        String actDeal = response.jsonPath().get("name");
        Assert.assertEquals(dealName,actDeal);

        String actMileStone = response.jsonPath().get("milestone");
        Assert.assertEquals(mileStone,actMileStone);
    }

    @Given("I setup a request structure to create Deal with file request body")
    public void iSetupARequestStructureToCreateDealWithFileRequestBody() throws IOException, ParseException {
        String filePath = System.getProperty("user.dir")+"/src/test/resources/RequestFiles/CreateDealRequest.json";
        File file = new File(filePath);


        FileReader fileReader = new FileReader(file);

        JSONParser jsonParser = new JSONParser();
        Object inputObject = jsonParser.parse(fileReader);
        JSONObject jsonObject = (JSONObject) inputObject;

        dealName = (String) jsonObject.get("name");
        expectedValue = (String)jsonObject.get("expected_value");

        specification= RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(file)
                .log().all();



    }

    @Then("I verify with deal created successfully with {int} status code")
    public void iVerifyWithDealCreatedSuccessfullyWithStatusCode(int statuscode) {
        response.prettyPrint();
        Assert.assertEquals(statuscode,response.getStatusCode());

        String actName = response.jsonPath().get("name");
        Assert.assertEquals(dealName,actName);

//        String actExpectedVal = response.jsonPath().get("expected_value").toString();
//        Assert.assertEquals(expectedValue,actExpectedVal);
    }

    @Given("I setup a request structure to Create deal")
    public void iSetupARequestStructureToCreateDeal() {
        createDealPojo = new CreateDealPojo();
        createDealPojo.setName(faker.name().firstName());
        createDealPojo.setExpected_value(1400f);
        createDealPojo.setProbability(80f);
        createDealPojo.setClose_date(14358796486L);
        createDealPojo.setMilestone("Referral");
        List<Long> id = new ArrayList<>(List.of(5694691248963585l,5694691248963595l));

        Map<String,String> map1 = new HashMap<>();
        map1.put("name","Group Size");
        map1.put("value","20");

        List<Map<String,String>> list = new ArrayList<>();

        list.add(map1);

        createDealPojo.setContact_ids(id);
        createDealPojo.setCustom_data(list);

        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(createDealPojo)
                .log().all();
    }

    @When("I hit an api to Create Deal")
    public void iHitAnApiToCreate() {
        response = specification.post("opportunity");

    }

    @Then("I verify Deal Created successfully")
    public void iVerifyDealCreatedSuccessfully() {
        response.prettyPrint();
        Assert.assertEquals(200,response.getStatusCode());
    }

    @Then("I verify Deal Created successfully with Deserialization")
    public void iVerifyDealCreatedSuccessfullyWithDeserialization() {

        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

        createDealResponsePojo = response.as(CreateDealResponsePojo.class);
        List<Map<String,String>> actCustomData = createDealResponsePojo.getCustom_data();
        List<Map<String,String>> expCustomData = createDealPojo.getCustom_data();

        Assert.assertEquals(expCustomData,actCustomData);

        String actName = createDealResponsePojo.getName();
        String expName = createDealPojo.getName();
        Assert.assertEquals(expName,actName);

        Float actExpVal = createDealResponsePojo.getExpected_value();
      //  Long actExpValue = (Long.valueOf(actExpVal));
        Float expExpVal = createDealPojo.getExpected_value();
        System.out.println(actExpVal +" : " + expExpVal );
        Assert.assertEquals(expExpVal,actExpVal);
    }


    @Given("I setup a request to create Deal using request body using Serialization")
    public void iSetupARequestToCreateDealUsingRequestBodyUsingSerialization() {
        
        createDealPojo= new CreateDealPojo();

        createDealPojo.setName("Republic Day Deal");
        createDealPojo.setExpected_value(1000F);
        createDealPojo.setMilestone("Proposal");
        createDealPojo.setProbability(90F);
        createDealPojo.setClose_date(1455042600L);
        List<Long> ids = new ArrayList<>(List.of(5170945558642688l, 6414762953736192l));
        createDealPojo.setContact_ids(ids);
        List<Map<String,String>> list = new ArrayList<>();
        Map<String,String> customDataMap = new HashMap<>(Map.of("name","Group Size","value","10"));
        list.add(customDataMap);
        createDealPojo.setCustom_data(list);

        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(createDealPojo)
                .log().all();

    }

    @When("I hit an api to create deal")
    public void iHitAnApiToCreatedeal() {
        response = specification.post("opportunity");

        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());


    }

    @Then("I verify created Deal in the response using DeSerialization")
    public void iVerifyCreatedDealInTheResponseUsingDeSerialization() {

        createDealResponsePojo = response.as(CreateDealResponsePojo.class);
        String actName = createDealResponsePojo.getName();
        String expName =createDealPojo.getName();

        Assert.assertEquals(expName,actName);

        String actMilestone = createDealResponsePojo.getMilestone();
        String expMileStone = createDealPojo.getMilestone();

        Assert.assertEquals(expMileStone,actMilestone);

        Float actExpVal = createDealResponsePojo.getExpected_value();
        Float expExpVal = createDealPojo.getExpected_value();

        Assert.assertEquals(expExpVal,actExpVal);




    }

    @When("I update the name and url of Deal")
    public void iUpdateTheNameAndUrlOfDeal() {

        createDealPojo.setName(faker.name().firstName());
        createDealPojo.setExpected_value(1200F);
        createDealPojo.setProbability(99F);
        createDealPojo.setId(createDealResponsePojo.getId());
        specification = RestAssured.given();
        response = specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(createDealPojo)
                .log().all().put("opportunity/partial-update");

        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

    }

    @Then("I verify response in particular Deal using DeSerialization")
    public void iVerifyResponseInParticularDealUsingDeSerialization() {

        createDealResponsePojo = response.as(CreateDealResponsePojo.class);
        String actName = createDealResponsePojo.getName();
        String expName =createDealPojo.getName();

        Assert.assertEquals(expName,actName);

        Float actExpVal = createDealResponsePojo.getExpected_value();
        Float expExpVal = createDealPojo.getExpected_value();

        Assert.assertEquals(expExpVal,actExpVal);

        Float actProbability = createDealResponsePojo.getProbability();
        Float expProbability = createDealPojo.getProbability();

        Assert.assertEquals(expProbability,actProbability);
    }

    @And("I verify response in get all Deal using Deserialization")
    public void iVerifyResponseInGetAllDealUsingDeserialization() {

        specification = RestAssured.given();
        response = specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all().get("opportunity");

        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

        CreateDealResponsePojo[] createDeal = response.as(CreateDealResponsePojo[].class);
        for(CreateDealResponsePojo dealObject : createDeal){
           if(dealObject.getName().equals(createDealPojo.getName())) {
               Long actId = dealObject.getId();
               Long expId = createDealPojo.getId();

               Assert.assertEquals(expId, actId);
           }
        }

    }

    @When("I delete Deal")
    public void iDeleteDeal() {


        specification = RestAssured.given();
        response = specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/opportunity/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .pathParam("DealId",createDealPojo.getId())
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all().delete("{DealId}");

        response.prettyPrint();

        Assert.assertEquals(204,response.getStatusCode());


    }

    @Then("I verify delete Deal Successfully in particular Deal Api")
    public void iVerifyDeleteDealSuccessfullyInParticularDealApi() {

        specification = RestAssured.given();
        response = specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/opportunity/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .pathParam("DealId",createDealPojo.getId())
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all().get("{DealId}");

        response.prettyPrint();

        Assert.assertEquals(204,response.getStatusCode());

    }

    @And("I verify Deal deleted successfully in get all Deal Api")
    public void iVerifyDealDeletedSuccessfullyInGetAllDealApi() {

        specification = RestAssured.given();
        response = specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all().get("opportunity");

        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

        List<Long> listId = response.jsonPath().getList("id");
        System.out.println(listId);
        System.out.println(createDealPojo.getId());
        Assert.assertFalse(createDealPojo.getId().equals(listId));

    }
}
