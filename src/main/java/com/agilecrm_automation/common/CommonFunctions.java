package com.agilecrm_automation.common;

import io.restassured.http.Method;
import io.restassured.response.Response;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CommonFunctions {

    ApiRequestBuilder requestBuilder = new ApiRequestBuilder();

    public Response getResponse(String endpoint) throws IOException {

        requestBuilder.setupRequestConfigs();
        requestBuilder.execute(Method.GET,endpoint);
        return requestBuilder.response;
    }

    public Response getResponseWithQueryParams(Map<String,String> queryParams, String endpoint) throws IOException {

        requestBuilder.setupRequestConfigs();
        requestBuilder.setQueryParams(queryParams);
        requestBuilder.execute(Method.GET,endpoint);
        return requestBuilder.response;
    }

    public Response getResponseWithRequestBody(File file,String endpoint) throws IOException {
        requestBuilder.setupRequestConfigs();
        requestBuilder.setRequestBody(file);
        requestBuilder.execute(Method.POST,endpoint);
        return requestBuilder.response;


    }



}
