package com.agilecrm_automation.stepdefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.io.*;
import java.util.Map;
import java.util.Set;

public class FileUploadDownStepDef {

    RequestSpecification specification;
    Response response;
    File file;

    @Given("I setup a request structure to upload file")
    public void setup(){

        String filePath = "C:\\Users\\Shree\\Downloads/KHRMS2 Employee Onboard Test Cases.xlsx";
        file = new File(filePath);
        specification = RestAssured.given();
        specification.baseUri("https://postman-echo.com")
                .basePath("/post")
                .contentType(ContentType.MULTIPART)
                .multiPart(file)
                .log().all();

    }

    @When("I hit an api to upload file")
    public void iHitAnApiToUploadFile() {
        response = specification.post();
    }

    @Then("I verify file upload successfully in response")
    public void iVerifyFileUploadSuccessfullyInResponse() {
        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

        Map<String,String> fileObject = response.jsonPath().getMap("files");

//        Set<String> keySet = fileObject.keySet();
//        String expFileName = file.getName();
//
//        for(String str : keySet){
//            if(str.equals(expFileName)){
//                Assert.assertEquals(str,expFileName);
//            }
//        }

        Assert.assertTrue(fileObject.keySet().contains(file.getName()));
    }

    @Given("I setup a request structure to Download file")
    public void iSetupARequestStructureToDownloadFile() {

        specification = RestAssured.given();
        specification.baseUri("https://raw.githubusercontent.com/")
                .basePath("shevkari/cyber/master/AgileCrm/")
                .contentType(ContentType.ANY)
                .log().all();
    }

    @When("I hit an api to Download file")
    public void iHitAnApiToDownloadFile() {
        response = specification.get("pom.xml");
    }

    @Then("I verify file Downloaded successfully in response using InputStream")
    public void iVerifyFileDownloadedSuccessfullyInResponseUsingInputStream() throws IOException {
        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

        InputStream inputStream = response.asInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\Shree\\Downloads/POM.xml");
        fileOutputStream.write(inputStream.readAllBytes());
        fileOutputStream.flush();
        fileOutputStream.close();


    }

    @Then("I verify file Downloaded successfully in response using ByteStream")
    public void iVerifyFileDownloadedSuccessfullyInResponseUsingByteStream() throws IOException {
        response.prettyPrint();

        Assert.assertEquals(200,response.getStatusCode());

        byte[] byteArray = response.asByteArray();

        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Shree\\Downloads/POM.xml");
        outputStream.write(byteArray);
        outputStream.flush();
        outputStream.close();

          file = new File("C:\\Users\\Shree\\Downloads");
          String[] files = file.list();

          for(String fileName : files){
              if(fileName.equals("pom.xml")){
                  Assert.assertEquals(fileName,"pom.xml");
              }
          }

    }
}
