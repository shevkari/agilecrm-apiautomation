package com.agilecrm_automation.common;

import io.restassured.http.Method;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.Map;

public class CommonFunctions {
    public Response getResponse(String endpoint) throws IOException {
        ApiRequestBuilder requestBuilder = new ApiRequestBuilder();
        requestBuilder.setupRequestConfigs();
        requestBuilder.execute(Method.GET,endpoint);
        return requestBuilder.response;
    }

    public Response getResponseWithQueryParams(Map<String,String> queryParams, String endpoint) throws IOException {
        ApiRequestBuilder requestBuilder = new ApiRequestBuilder();
        requestBuilder.setupRequestConfigs();
        requestBuilder.setQueryParams(queryParams);
        requestBuilder.execute(Method.GET,endpoint);
        return requestBuilder.response;

    }

}
