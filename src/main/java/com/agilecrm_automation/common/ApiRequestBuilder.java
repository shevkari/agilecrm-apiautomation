package com.agilecrm_automation.common;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;


public class ApiRequestBuilder {
    RequestSpecification specification;
    Response response;

    public void setupRequestConfigs() throws IOException {
        specification = RestAssured.given();
       PropertyHandler propertyHandler = new PropertyHandler("config.properties");
        String baseUri = propertyHandler.getProperty("baseUri");
        String basePath = propertyHandler.getProperty("basePath");
        String token = propertyHandler.getProperty("token");
        specification.baseUri(baseUri)
                .basePath(basePath)
                .header("Authorization",token)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all();
    }


    public void execute(Method method, String endpoint) throws IOException {
        PropertyHandler propertyHandler = new PropertyHandler("endpoints.properties");
        switch (method){
            case GET:
                response = specification.get(propertyHandler.getProperty(endpoint));
                break;
            case POST:
                response = specification.post(propertyHandler.getProperty(endpoint));
                break;
            case PATCH:
                response = specification.patch(propertyHandler.getProperty(endpoint));
                break;
            case PUT:
                response = specification.put(propertyHandler.getProperty(endpoint));
                break;
            case DELETE:
                response = specification.delete(propertyHandler.getProperty(endpoint));
                break;
        }
    }


    public void setHeaders(Map<String,String> headers){
        if(headers!=null && !headers.isEmpty()){
            specification.headers(headers);
        }
    }

    public void setQueryParams(Map<String,String> quertParams){
        if(quertParams!=null && !quertParams.isEmpty()){
            specification.queryParams(quertParams);
        }
    }

    public void setRequestBody(String body){
        if(body!=null){
            specification.body(body);
        }
    }


    public void setRequestBody(JSONObject requestBody){
        if(requestBody!=null){
            specification.body(requestBody);
        }
    }


    public void setRequestBody(File file){
        if(file!=null){
            specification.body(file);
        }
    }


    public void setRequestBody(Object object){
        if(object!=null){
            specification.body(object);
        }
    }



}