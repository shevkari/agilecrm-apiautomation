package com.agilecrm_automation.common;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class MockServerConfig {

    public static WireMockServer mockServer;

    public static void startServer(){

        mockServer = new WireMockServer(WireMockConfiguration.options().port(8081));

        WireMock.configureFor("localhost",8081);

        mockServer.start();

    }

    public static void stubForGet(){
        String respBody ="{\n" +
                "    \"data\": {\n" +
                "        \"id\": 1,\n" +
                "        \"email\": \"george.bluth@reqres.in\",\n" +
                "        \"first_name\": \"George\",\n" +
                "        \"last_name\": \"Bluth\",\n" +
                "        \"avatar\": \"https://reqres.in/img/faces/1-image.jpg\"\n" +
                "    },\n" +
                "    \"support\": {\n" +
                "        \"url\": \"https://reqres.in/#support-heading\",\n" +
                "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                "    }\n" +
                "}";

        WireMock.stubFor(WireMock.get(urlEqualTo("/contact"))
                .willReturn(aResponse()
                        .withHeader("Content-type", ContentType.JSON.toString())
                        .withBody(respBody)));
    }

//    public static void main(String[] agr){
//
//        startServer();
//
//        stubForGet();
//
//        RequestSpecification specification = RestAssured.given();
//        Response response = specification.baseUri("http://localhost:8081")
//                .log().all()
//                .get("/contact");
//
//        response.prettyPrint();
//
//
//
//    }
    public static void stopServer() {
        mockServer.stop();
    }
}
