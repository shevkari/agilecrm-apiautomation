package com.agilecrm_automation.stepdefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class TestStepDef {

    RequestSpecification specification;
    Response response;
        @Given("I setup a request Structure to get information of name values")
        public void iSetupReqStructure(){
            specification= RestAssured.given();
            specification.baseUri("https://gorest.co.in/graphql/")
                    .basePath("schema.json")
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .log().all();
        }

        @When("I hit a api to get details")
        public void iHitAnApi() {
            response = specification.get();
        }

        @Then("I verify response with graphql data")
        public void iVerifyResponse() {
        //    response.prettyPrint();

            Assert.assertEquals(200, response.getStatusCode());

//            List<Map<String, Object>> objList = response.jsonPath().getList("data.__schema.types");
//        //    System.out.println(objList);
//            for(Map<String,Object> objMap : objList){
//        //        System.out.println(objMap);
//                Map<String,Object> mapObj =(Map<String,Object>) objMap;
//        //        System.out.println(mapObj);
//               List<Map<String,Object>> listObj =(List<Map<String,Object>>)  mapObj.get("fields");
//  //                 System.out.println(listObj);
//               if(listObj==null){
//                    System.out.println(mapObj.get("name"));
//                }
//             }



            List<Object> fielsdList = response.jsonPath().getList("data.__schema.types.fields");
    //        System.out.println(fielsdList);
         for(Object fields : fielsdList){
             if(fields!=null) {
                 List<Map<String,Object>> ll = (List<Map<String,Object>>)fields;
         //        System.out.println(ll);
                 for(Map<String,Object> mapObj : ll ){
                    Map<String,Object> typeMap = (Map<String,Object>) mapObj.get("type");
         //           System.out.println(typeMap);
                     Map<String,Object> typeOf  =(Map<String,Object>)typeMap.get("ofType");
         //            System.out.println(typeOf);
                     if(typeOf!=null){
         //                System.out.println(typeOf);
                        String kind = (String)typeOf.get("kind");
         //                System.out.println(kind);

                         Map<String,Object> nextOfType = (Map<String,Object>) typeOf.get("ofType");
                 //        System.out.println(nextOfType);
                         if(nextOfType!=null){
                             String kindValue = (String)nextOfType.get("kind");
                             if(kindValue.equals("NON_NULL")){
                                 Map<String,String> nextONextType= (Map<String,String>) nextOfType.get("ofType");
                         //        System.out.println(nextONextType);
                                 String nameValue =  nextONextType.get("name");
                          //       System.out.println(nameValue);
                             }
                         }
                     }

                 }
             }
         }

        List<List<Object>> objMap =  response.jsonPath().getList("data.__schema.types.fields.type.ofType.ofType.ofType.name");
    //       System.out.println(objMap);
            for(List<Object> map : objMap){
        //        System.out.println(map);
                for(Object OBJ : map){
                    String name = OBJ.toString();
                    if(name.equals("__Directive"))
                    Assert.assertEquals("__Directive",name);
                }

            }

        }


    @Given("I setup a request structure to get information of active users")
    public void iSetupARequestStructureToGetInformationOfActiveUsers(Map<String,String> queryParam) {
            specification = RestAssured.given();
            specification.baseUri("https://gorest.co.in/")
                    .basePath("/public/v2/users")
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .queryParams(queryParam)
                    .log().all();

    }

    @When("I hit an api to get details")
    public void iHitAnApiToGetDetails() {
            response=specification.get();

    }

    @Then("I verify response with active users")
    public void iVerifyResponseWithActiveUsers() {
            response.prettyPrint();
            Assert.assertEquals(200,response.getStatusCode());

            List<String> statusList = response.jsonPath().getList("status");
    //    System.out.println(statusList);
        String expStatus = "active";
        for(String actStatus : statusList) {
            Assert.assertEquals(expStatus,actStatus);
        }
    }

    @Given("I setup a request structure to get information of all users")
    public void iSetupARequestStructureToGetInformationOfAllUsers() {
            specification = RestAssured.given();
            specification.baseUri("https://gorest.co.in/")
                    .basePath("public/v2/users")
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .log().all();
    }

    @When("I hit an api to get details of all users")
    public void iHitAnApiToGetDetailsOfAllUsers() {
            response = specification.get();
    }

    @Then("I verify response with active and inactive users")
    public void iVerifyResponseWithActiveAndInactiveUsers() {
            response.prettyPrint();

            Assert.assertEquals(200,response.getStatusCode());

            int activeCount=0, inactiveCount=0;
           List<String> statusList =  response.jsonPath().get("status");
       for(String Status : statusList){
           if(Status.equals("active"))
           {
               activeCount++;
           }
           else
           {
               inactiveCount++;
           }
       }
        System.out.println("Active users Are :  "+activeCount);
        System.out.println("Inactive users Are :  "+inactiveCount);
    }
}
