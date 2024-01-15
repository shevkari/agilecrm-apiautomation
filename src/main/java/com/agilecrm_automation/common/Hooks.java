package com.agilecrm_automation.common;

import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Hooks {

    @BeforeAll
    public static void init(){
        MockServerConfig.startServer();
        MockServerConfig.stubForGet();
    }

    @AfterAll
    public static void tearDown(){
        MockServerConfig.stopServer();
    }

    @Before("@GetMockData")
    public void getMockData(){
        RequestSpecification specification = RestAssured.given();
        Response response = specification.baseUri("http://localhost:8081")
                .basePath("/contacts")
                .header("Accept",ContentType.JSON)
                .log().all()
                .get("/contact");

        response.prettyPrint();
    }
}
