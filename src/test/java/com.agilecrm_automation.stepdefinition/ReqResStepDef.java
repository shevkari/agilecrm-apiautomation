package com.agilecrm_automation.stepdefinition;

import com.agilecrm_automation.pojo.CreateUserPojo;
import com.agilecrm_automation.pojo.CreateUserResponsePojo;
import com.agilecrm_automation.pojo.UserResponsePojo;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

import static com.agilecrm_automation.common.MockServerConfig.startServer;
import static com.agilecrm_automation.common.MockServerConfig.stubForGet;

public class ReqResStepDef {

    RequestSpecification specification;
    Response response;
    CreateUserPojo createUserPojo;
    UserResponsePojo userResponsePojo;

    CreateUserResponsePojo  createUserResponsePojo;

    @Given("I setup a request structure to get user information")
    public void setUp(){
        specification = RestAssured.given();
        specification.baseUri("https://reqres.in/")
                .basePath("api/users")
                .log().all();
        }


    @When("I hit get all users api")
    public void iHitGetAllUsersApi() {
        response = specification.get("api/users");
    }


    @Then("I verify response with users")
    public void iVerifyResponseWithUsers() {
        response.prettyPrint();

        int statuscode = response.getStatusCode();

        Assert.assertEquals(200,statuscode);
        List<Long> idlist = response.jsonPath().get("data.id");
        System.out.println(idlist);

        List<String> emaillist = response.jsonPath().get("data.email");
        System.out.println(emaillist);

        Map<String,Object> dataobj = response.jsonPath().getMap("data[0]");
        System.out.println(dataobj);

        Long pageNum = response.jsonPath().getLong("page");
        System.out.println(pageNum);



        List<Object> datalist = response.jsonPath().getList("data");
        int listSize = datalist.size();

        int perPage = response.jsonPath().getInt("per_page");

        Assert.assertEquals("Records NOT According to page size",listSize,perPage);

        if(listSize<=perPage){
            System.out.println("Records According to page size");
        }
        else{
            System.out.println("Records NOT According to page size");
        }
    }

    @Then("I verify total users in response")
    public void iVerifyTotalUsersInResponse() {
        // get total no of pages available with user info
        Long totalPages = response.jsonPath().getLong("total_pages");

        //get total numbers of records available in response
        Long totalRecords = response.jsonPath().getLong("total");

        List<Object> dataList = response.jsonPath().getList("data");

        int firstPageSize = dataList.size();
        //Top casting int into the Long
        Long size = Long.parseLong(String.valueOf(firstPageSize));

        Long perPageSize = response.jsonPath().getLong("per_page");

        Assert.assertEquals(perPageSize,size);

        long nextPageRecords = totalRecords-firstPageSize;
        long leftRecords=0;

        for(int i=2; i<=3; i++){

            specification = RestAssured.given();
           Response resp = specification.baseUri("https://reqres.in/")
                    .queryParams("page",i)
                    .basePath("api/users")
                    .log().all().get();

         //  resp.prettyPrint();

           long dataSize = resp.jsonPath().getList("data").size();

           leftRecords = totalRecords-dataSize;
            
           Assert.assertTrue(dataSize <= leftRecords);

           if(leftRecords==0){
               break;
           }

        }

    }


    @Given("I setup a request structure to get user info")
    public void iSetupARequestStructureToGetUserInfo() {
        specification = RestAssured.given();
         specification.baseUri("https://reqres.in/")
                .basePath("api/users")
                .log().all().get();
    }

    @When("I hit an api to get all users")
    public void iHitAnApiToGetAllUsers() {
        response= specification.get();
    }

    @Then("I verify particular user in response")
    public void iVerifyParticularUserInResponse() {
        response.prettyPrint();
//        List<Object> dataObj = response.jsonPath().get("data");
//    //    System.out.println(dataObj);
//        for(Object obj : dataObj){
//            Map<String,Object> dataMap = (Map<String,Object>)obj;
//          String dataStr  =dataMap.get("email").toString();
//          //  System.out.println(dataStr);
//
//        if(dataStr.equals("emma.wong@reqres.in")) {
//            System.out.println(dataStr);
//            Assert.assertEquals("emma.wong@reqres.in",dataStr);
//        }
//        }

        List<String> emailList = response.jsonPath().getList("data.email");
        System.out.println(emailList.size());

        String actEmail = "emma.wong@reqres.in";
        for(int i=0; i<emailList.size(); i++){
            Assert.assertEquals(emailList.get(i),actEmail);
       }
    }

    @Given("I setup request structure")
    public void iSetupRequestStructure() {
        specification = RestAssured.given();
        specification.baseUri("https://reqres.in/")
                .basePath("api/users")
                .log().all();

    }

    @When("I hit an api to get info")
    public void iHitAnApiToGetInfo() {
        response = specification.get();
    }

    @Then("I verify the response")
    public void iVerifyTheResponse() {
        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

        List<Long> dataList = response.jsonPath().get("data.id");
        System.out.println(dataList);

        for(int i =0; i<dataList.size(); i++)
        {
            Long id = 2L;
            Assert.assertEquals(id,dataList.get(i));
        }
//
//        List<Object> dataList = response.jsonPath().getList("data");
//     //   System.out.println(dataList);
//        for(Object obj : dataList){
//            Map<String,Object> dataMap = (Map<String,Object>)obj;
//    //       System.out.println(dataMap);
//           String actEmail = dataMap.get("email").toString();
//    //        System.out.println(actEmail);
//
//
//
//            String expEmail = "charles.morris@reqres.in";
//            if(actEmail.equals("charles.morris@reqres.in")) {
//                Assert.assertEquals(expEmail, actEmail);
//            }
//        }
    }

    @Given("I setup request structure to create user")
    public void iSetupRequestStructureToCreateUser() {

         createUserPojo = new CreateUserPojo();
        createUserPojo.setName("GoodLuck Cafe");
        createUserPojo.setJob("Customer Service");

        specification = RestAssured.given();
        specification.baseUri("https://reqres.in/")
                .basePath("api/users")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(createUserPojo)
                .log().all();
    }

    @When("I hit a api to create user")
    public void iHitAApiToCreateUser() {
        response=specification.post();
    }

    @Then("I verify user created successfully")
    public void iVerifyUserCreatedSuccessfully() {
        response.prettyPrint();

        Assert.assertEquals(201,response.getStatusCode());

       String actUserName = response.jsonPath().get("name");
       String expUserName = "GoodLuck Cafe";

       Assert.assertEquals(expUserName,actUserName);

       String actJob = response.jsonPath().get("job");
       String expJob = "Customer Service";
       Assert.assertEquals(expJob,actJob);
    }

    @Given("I setup request structure to get user")
    public void iSetupRequestStructureToGetUser() {
        specification = RestAssured.given();
        specification.baseUri("https://reqres.in/")
                .basePath("api/users")
                .log().all();

    }

    @When("I hit a api to get user")
    public void iHitAApiToGetUser() {
        response = specification.get();
    }

    @Then("I verify user Retrieve successfully")
    public void iVerifyUserRetrieveSuccessfully() {
        response.prettyPrint();
        Assert.assertEquals(200 ,response.getStatusCode());

        userResponsePojo =response.as(UserResponsePojo.class);

        System.out.println(userResponsePojo.getPage());
        System.out.println(userResponsePojo.getPer_page());
        System.out.println(userResponsePojo.getTotal());
        System.out.println(userResponsePojo.getTotal_pages());
        System.out.println(userResponsePojo.getData());
        System.out.println(userResponsePojo.getSupport());
        for(Map<String,Object> dataMap : userResponsePojo.getData()){
            System.out.println(dataMap.get("id"));
        }
    }

    @Given("I setup a request structure to create user by using serialization")
    public void iSetupARequestStructureToCreateUserByUsingSerialization() {
        createUserPojo = new CreateUserPojo();
        createUserPojo.setName("IronMan");
        createUserPojo.setJob("World Saviour");
        specification = RestAssured.given();
        specification.baseUri("https://reqres.in/")
                .basePath("api/users")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(createUserPojo)
                .log().all();


    }

    @Then("I verify response using deserialization")
    public void iVerifyResponseUsingDeserialization() {
        response.prettyPrint();
        Assert.assertEquals(201,response.getStatusCode());

        createUserResponsePojo = response.as(CreateUserResponsePojo.class);

        String expName = createUserPojo.getName();
        String actName = createUserResponsePojo.getName();

        Assert.assertEquals(expName,actName);

        String expJob = createUserPojo.getJob();
        String actJob = createUserResponsePojo.getJob();
        Assert.assertEquals(expJob,actJob);
    }

    @Given("I setup request structure and hit an api to get data from mock server")
    public void iSetupRequestStructureAndHitAnApiToGetDataFromMockServer() {

            specification = RestAssured.given();
            response = specification.baseUri("http://localhost:8081")
                .log().all()
                .get("/contact");



    }

    @Then("I verify with response of Mock data")
    public void iVerifyWithResponseOfMockData() {
        response.prettyPrint();
    }
}
