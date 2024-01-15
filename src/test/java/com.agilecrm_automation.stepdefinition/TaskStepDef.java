package com.agilecrm_automation.stepdefinition;

import com.agilecrm_automation.pojo.CreateTaskPojo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskStepDef {

    RequestSpecification specification;
    Response response;
    String taskType;
    String taskPriority;

    @Given("I setup a request structure to create Task with following info")
    public void iSetupARequestStructureToCreateTaskWithFollowingInfo(Map<String,String> dataMap) {
        taskType = dataMap.get("type");
        taskPriority = dataMap.get("priority_type");

        String reqBody="{\n" +
                "    \"progress\": \"0\",\n" +
                "    \"subject\": \"Need to contact\",\n" +
                "    \"type\": \""+taskType+"\",\n" +
                "    \"due\": 1456986600,\n" +
                "    \"task_ending_time\": \"12:00\",\n" +
                "    \"priority_type\": \""+taskPriority+"\",\n" +
                "    \"status\": \"YET_TO_START\",\n" +
                "    \"contacts\": [\n" +
                "        \"5725836472745984\"\n" +
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

    @When("I hit an api to create Task")
    public void iHitAnApiToCreateTask() {
        response = specification.post("tasks");
    }

    @Then("I verify with Task created Successfully with {int} status code")
    public void iVerifyWithTaskCreatedSuccessfullyWithStatusCode(int statuscode) {
        response.prettyPrint();

        Assert.assertEquals(statuscode,response.getStatusCode());
    }

    @Given("I setup a request structure to create Task with following HashMap info")
    public void iSetupARequestStructureToCreateTaskWithFollowingHashMapInfo(DataTable table) {
        List<Map<String,String>> dataList = table.asMaps();
        Map<String,String> dataMap = dataList.get(0);

        taskType = dataMap.get("type");
        taskPriority = dataMap.get("priority_type");

        Map<String,String> reqBody = new HashMap<>();

        if(taskType==null){
            reqBody.put("type",null);
            reqBody.put("priority_type",taskPriority);
        }else if(taskPriority==null){
            reqBody.put("type",taskType);
            reqBody.put("priority_type",null);
        }else if(taskType==null && taskPriority==null){
            reqBody.put("type",null);
            reqBody.put("priority_type",null);
        }else{
            reqBody.put("type",taskType);
            reqBody.put("priority_type",taskPriority);
        }

        specification= RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(reqBody)
                .log().all();


    }

    @Then("I verify with Task created Successfully with {int} code")
    public void iVerifyWithTaskCreatedSuccessfullyWithStatuscodeStatusCode(int statuscode) {
        response.prettyPrint();
        Assert.assertEquals(statuscode,response.getStatusCode());

        String actTaskType = response.jsonPath().get("title");
        Assert.assertEquals(taskType,actTaskType);

        String actTaskPriority = response.jsonPath().get("priority_type");
        Assert.assertEquals(taskPriority,actTaskPriority);
    }

    @Given("I setup a request structure to create Task with File body")
    public void iSetupARequestStructureToCreateTaskWithFileBody() throws IOException, ParseException {

        String filePath = System.getProperty("user.dir")+"/src/test/resources/RequestFiles/CreateTaskRequest.json";
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        JSONParser jsonParser = new JSONParser();
        Object inputObject = jsonParser.parse(fileReader);
        JSONObject jsonObject = (JSONObject) inputObject;
        taskType = (String)jsonObject.get("type");
        taskPriority = (String)jsonObject.get("priority_type");

        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(file)
                .log().all();
    }

    @Given("I setup a request structure to Create Task")
    public void iSetupARequestStructureToCreateTask() {
        CreateTaskPojo CTP = new CreateTaskPojo();
        CTP.setProgress("25");
        CTP.setSubject("need to connect");
        CTP.setType("Email");
        CTP.setDue(1556986600L);
        CTP.setTask_ending_time("15:40");
        CTP.setPriority_type("HIGH");
        CTP.setStatus("IN_PROGRESS");
        String[] contact = new String[]{"4898708905590784","4898708905590790"};

       CTP.setContacts(contact);

        specification = RestAssured.given();
        specification.baseUri("https://restapi.agilecrm.com/")
                .basePath("dev/api/")
                .auth().basic("Rest@yopmail.com","neie86ad56qv3ku0mn9o16ogg5")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(CTP)
                .log().all();
    }

    @When("I hit an api to Create Task")
    public void iHitAnApiToCreate() {
        response= specification.post("tasks");
    }

    @Then("I verify Task Created successfully")
    public void iVerifyTaskCreatedSuccessfully() {
        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

        CreateTaskPojo ctp = new CreateTaskPojo();
        String tet = ctp.getTask_ending_time();

        String actTet = response.jsonPath().get("task_ending_time");
        Assert.assertEquals(tet,actTet);

        List<Long> id = response.jsonPath().getList("contacts.id");
        System.out.println(id);
        String[] arr1 ={"4898708905590784"};
        ctp.setContacts(arr1);
       String[] arr = ctp.getContacts();
        Long l = Long.valueOf(arr[0]);

        Assert.assertEquals(l,id.get(0));
    }

    @Given("I setup a request to create Task using request body using Serialization")
    public void iSetupARequestToCreateTaskUsingRequestBodyUsingSerialization() {
    }

    @When("I hit an api to Create Task using Serialization")
    public void iHitAnApiToCreateTaskUsingSerialization() {
    }

    @Then("I verify created Task in the response using DeSerialization")
    public void iVerifyCreatedTaskInTheResponseUsingDeSerialization() {
    }

    @When("I update the name and url of Task")
    public void iUpdateTheNameAndUrlOfTask() {
    }

    @Then("I verify response in particular Task using DeSerialization")
    public void iVerifyResponseInParticularTaskUsingDeSerialization() {
    }

    @And("I verify response in get all Task using Deserialization")
    public void iVerifyResponseInGetAllTaskUsingDeserialization() {
    }

    @When("I delete Task")
    public void iDeleteTask() {
    }

    @Then("I verify delete Task Successfully in particular Task Api")
    public void iVerifyDeleteTaskSuccessfullyInParticularTaskApi() {
    }

    @And("I verify Task deleted successfully in get all Task Api")
    public void iVerifyTaskDeletedSuccessfullyInGetAllTaskApi() {
    }
}