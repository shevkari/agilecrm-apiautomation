package com.agilecrm_automation.stepdefinition;

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

public class DummyExampleStep {

    RequestSpecification specification;
    Response response;

    @Given("I set request structure to get all employee info")
    public void iSetReq()
    {
        specification = RestAssured.given();
        specification.baseUri("https://dummy.restapiexample.com/")
                .basePath("api/v1/")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all();

    }

    @When("I hit an api to get employee")
    public void hitAnApiToGetEmployee()
    {
        response=specification.get("employees");
    }

    @Then("I verify with employee response")
    public void getResponse()
    {
        response.prettyPrint();
        Assert.assertEquals(200,response.getStatusCode());

        String status = response.jsonPath().get("status");
        Assert.assertEquals("success",status);

        String message = response.jsonPath().get("message");
        String actMessage ="Successfully! All records has been fetched.";
        Assert.assertEquals(actMessage,message);

        List<Object> dataList = response.jsonPath().get("data");


        for(Object obj:dataList){
            Map<String,Object> dataMap =( Map<String,Object>) obj;
           String objList = dataMap.get("employee_name").toString();


            if(dataList.equals("Haley Kennedy")){
                int salary = (int)dataMap.get("employee_salary");
                Assert.assertEquals(313500,salary);
            }
        }

        List<Integer> ageInt = response.jsonPath().getList("data.employee_age");
     //   System.out.println(ageInt);

        if(ageInt.get(4)==33){
            Integer age = 33;
            Assert.assertEquals(age,ageInt.get(4));
        }

    }
}
