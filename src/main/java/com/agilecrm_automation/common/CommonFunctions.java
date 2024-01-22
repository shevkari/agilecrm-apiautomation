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

    public Response getResponseWithPathParams(String pathParam, String endpoint) throws IOException {
        requestBuilder.setupRequestConfigs();
        requestBuilder.setPathParam(pathParam);
        requestBuilder.execute(Method.GET,endpoint);
        return requestBuilder.response;
    }

    public Response postWithRequestBody(File file,String endpoint) throws IOException {
        requestBuilder.setupRequestConfigs();
        requestBuilder.setRequestBody(file);
        requestBuilder.execute(Method.POST,endpoint);
        return requestBuilder.response;
    }

    public Response postWithRequestBody(String body,String endpoint) throws IOException {
        requestBuilder.setupRequestConfigs();
        requestBuilder.setRequestBody(body);
        requestBuilder.execute(Method.POST,endpoint);
        return requestBuilder.response;
    }

    public Response postWithRequestBody(Object object,String endpoint) throws IOException {
        requestBuilder.setupRequestConfigs();
        requestBuilder.setRequestBody(object);
        requestBuilder.execute(Method.POST,endpoint);
        return requestBuilder.response;
    }

    public Response putWithRequestBody(String body, String endpoint) throws IOException {
        requestBuilder.setupRequestConfigs();
        requestBuilder.setRequestBody(body);
        requestBuilder.execute(Method.PUT,endpoint);
        return requestBuilder.response;
    }

    public Response deleteWithPathParams(String pathParam, String endpoint) throws IOException {
        requestBuilder.setupRequestConfigs();
        requestBuilder.setPathParam(pathParam);
        requestBuilder.execute(Method.DELETE,endpoint);
        return requestBuilder.response;
    }

    public Response patchWithRequestBody(String body, String endpoint) throws IOException {
        requestBuilder.setupRequestConfigs();
        requestBuilder.setRequestBody(body);
        requestBuilder.execute(Method.PATCH,endpoint);
        return requestBuilder.response;
    }
}
