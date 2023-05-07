package br.com.ale.restassuredDemo.utils;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.net.URI;
import java.util.Map;

public class RestAssuredUtils {
    public static Response executeRequest(String url, String requestService, Method method, Map<String,String> headers,
                                          Map<String,String> cookies, Map<String,String> queryParameters, Object jsonBody,
                                          String authenticatiorUser, String authenticatorPassword){
        RequestSpecification requestSpecification = RestAssured.given();

        for(Map.Entry<String,String> header : headers.entrySet()){
            requestSpecification.headers(header.getKey(), header.getValue());
        }

        for(Map.Entry<String,String> cookie : cookies.entrySet()){
            requestSpecification.cookies(cookie.getKey(), cookie.getValue());
        }

        for(Map.Entry<String,String> parameter : queryParameters.entrySet()){
            requestSpecification.queryParams(parameter.getKey(), parameter.getValue());
        }

        if(jsonBody != null){
            requestSpecification.body(jsonBody);
        }
        return requestSpecification.request(method, URI.create(url+requestService));
    }

}
