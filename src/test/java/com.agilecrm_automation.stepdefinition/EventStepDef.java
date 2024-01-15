package com.agilecrm_automation.stepdefinition;

import com.agilecrm_automation.pojo.CreateEventPojo;
import com.agilecrm_automation.pojo.CreateEventResponsePojo;
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

public class EventStepDef {

    RequestSpecification specification;

    Response response;

    String eventName;

    String eventColour;
    CreateEventPojo createEventPojo;
    CreateEventResponsePojo createEventResponsePojo;
    Faker faker = new Faker(new Random());

    @Given("I setup a request structure to create Event with following info")
    public void iSetupARequestStructureToCreateEventWithFollowingInfo(Map<String,String> mapData) {

        eventName = mapData.get("title");
        eventColour = mapData.get("color");

        String reqBody = "{\n" +
                "    \"title\": \""+eventName+"\",\n" +
                "    \"allDay\": false,\n" +
                "    \"color\": \""+eventColour+"\",\n" +
                "    \"start\": 1409682600,\n" +
                "    \"end\": 1409768100,\n" +
                "    \"contacts\": [\n" +
                "        \"721001\",\n" +
                "        \"722001\"\n" +
                "    ]\n" +
                "}";

        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .body(reqBody)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all();
    }

    @When("I hit an api to create Event")
    public void iHitAnApiToCreateEvent() {
        response = specification.post("events");
    }

    @Then("I verify with Event created Successfully")
    public void iVerifyWithEventCreatedSuccessfully() {
        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

        String actTitle = response.jsonPath().get("title");
        Assert.assertEquals(eventName,actTitle);

        String actColor = response.jsonPath().get("color");
        Assert.assertEquals(eventColour,actColor);



    }

    @Given("I setup a request structure to create Event with following info using HashMap")
    public void iSetupARequestStructureToCreateEventWithFollowingInfoUsingHashMap(DataTable table) {
        List<Map<String,String>> dataList = table.asMaps();
        Map<String,String> dataMap = dataList.get(0);

        eventName = dataMap.get("title");
        eventColour = dataMap.get("color");

        Map<String,String> reqBody = new HashMap<>();

        if(eventName==null){
            reqBody.put("title",null);
            reqBody.put("color",eventColour);
        }else if(eventColour==null){
            reqBody.put("title",eventName);
            reqBody.put("color",null);
        }else if(eventName==null && eventColour==null){
            reqBody.put("title",null);
            reqBody.put("color",null);
        }else{
            reqBody.put("title",eventName);
            reqBody.put("color",eventColour);
        }

            specification = RestAssured.given();
            specification.baseUri("https://restapi.agilecrm.com/")
                    .basePath("dev/api/")
                    .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(reqBody)
                    .log().all();
    }

    @Then("I verify with Event created Successfully with {int} status code")
    public void iVerifyWithEventCreatedSuccessfullyWithStatuscodeStatusCode(int statusCode) {
        response.prettyPrint();

        Assert.assertEquals(statusCode,response.getStatusCode());
    }

    @Given("I setup a request structure to create Event with following info using File")
    public void iSetupARequestStructureToCreateEventWithFollowingInfoUsingFile() throws IOException, ParseException {
        String filePath =  System.getProperty("user.dir")+"/src/test/resources/RequestFiles/CreateEventRequest.json";
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        JSONParser jsonParser = new JSONParser();
        Object inputObject = jsonParser.parse(fileReader);
        JSONObject jsonObject = (JSONObject)inputObject;
        eventName = jsonObject.get("title").toString();
        eventColour = jsonObject.get("color").toString();
//       jsonObject.put("title","news event");
//       jsonObject.put("color","red");

        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(file)
            //    .body(jsonObject)
                .log().all();
    }

    @Then("I verify with Event created Successfully with status code {int}")
    public void iVerifyWithEventCreatedSuccessfullyWithStatusCode(int statuscode) {
        response.prettyPrint();

        Assert.assertEquals(statuscode,response.getStatusCode());

        String actEventName = response.jsonPath().get("title");
        Assert.assertEquals(eventName,actEventName);

        String actEventColour = response.jsonPath().get("color");
        Assert.assertEquals(eventColour,actEventColour);


    }

    @Given("I setup a request structure to Create Event")
    public void iSetupARequestStructureToCreateEvent() {
        createEventPojo = new CreateEventPojo();
        createEventPojo.setTitle("");
        createEventPojo.setAllDay(true);
        createEventPojo.setColor("blue");
        createEventPojo.setStart(1509682600L);
        createEventPojo.setEnd(1509682700L);

        List<Long> contacts = new ArrayList<>(List.of(999990l,999991l));
        createEventPojo.setContacts(contacts);

        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(createEventPojo)
                .log().all();
    }

    @When("I hit an api to Create Event")
    public void iHitAnApiToCreate() {
        specification.post("events");
    }

    @Then("I verify Event Created successfully")
    public void iVerifyEventCreatedSuccessfully() {
        response.prettyPrint();
        Assert.assertEquals(200,response.getStatusCode());
    }



    @Given("I setup a request to create Event using request body using Serialization")
    public void iSetupARequestToCreateEventUsingRequestBodyUsingSerialization() {

        createEventPojo = new CreateEventPojo();
        createEventPojo.setTitle(faker.weather().description());
        createEventPojo.setAllDay(true);
        createEventPojo.setColor(faker.color().name());
        createEventPojo.setStart(1435475595l);
        createEventPojo.setEnd(1435475809l);
        List<Long> contact = new ArrayList<>(List.of(4571972040982528l));
        createEventPojo.setContacts(contact);

        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .body(createEventPojo)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all();

    }

    @When("I hit an api to create event")
    public void iHitAnApiToCreateevent() {
        response = specification.post("events");


    }

    @Then("I verify created Event in the response using DeSerialization")
    public void iVerifyCreatedEventInTheResponseUsingDeSerialization() {

        response.prettyPrint();
        Assert.assertEquals(200,response.getStatusCode());

    //    createEventResponsePojo = response.as(CreateEventResponsePojo.class);


    }

    @When("I update the name and url of Event")
    public void iUpdateTheNameAndUrlOfEvent() {
    }

    @Then("I verify response in particular Event using DeSerialization")
    public void iVerifyResponseInParticularEventUsingDeSerialization() {
    }

    @And("I verify response in get all Event using Deserialization")
    public void iVerifyResponseInGetAllEventUsingDeserialization() {



    }

    @When("I delete Event")
    public void iDeleteEvent() {
    }

    @Then("I verify delete Event Successfully in particular Event Api")
    public void iVerifyDeleteEventSuccessfullyInParticularEventApi() {
    }

    @And("I verify Event deleted successfully in get all Event Api")
    public void iVerifyEventDeletedSuccessfullyInGetAllEventApi() {
    }
}
