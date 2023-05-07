package br.com.ale.restassuredDemo.bases;

import br.com.ale.restassuredDemo.dao.InsertDAO;
import br.com.ale.restassuredDemo.utils.GlobalParameters;
import br.com.ale.restassuredDemo.utils.RestAssuredUtils;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;

public class RequestRestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestRestBase.class);
    protected String url = GlobalParameters.URL_DEFAULT;
    protected String endPoint = null;
    protected Method method = null;
    protected Object jsonBody = null;
    protected Map<String,String> headers = new HashMap<>();
    protected Map<String,String> cookies = new HashMap<>();
    protected Map<String,String> queryParameters = new HashMap<>();
    protected String authenticatorUser = GlobalParameters.AUTHENTICATOR_USER;
    protected String authenticatorPassword = GlobalParameters.AUTHENTICATOR_PASSWORD;
    //protected static final String LOGIN_ENDPOINT="/api/rest/users/";


    public RequestRestBase(){
        enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public ValidatableResponse executeRequest(){
        Response response = RestAssuredUtils.executeRequest(url, endPoint,method,headers,cookies,queryParameters,jsonBody,authenticatorUser,authenticatorPassword);
        return response.then();
    }

    public void addHeader(String chave,String valor){
        headers.put(chave,valor);
    }

    public void setEndPoint(String endPoint){
        this.endPoint = endPoint;
    }

    /*
    public ValidatableResponse executeRequest(){
        //Response response = RestAssuredUtils.executeRequest();
        return ;
    }
    */
}
