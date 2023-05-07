package br.com.ale.restassuredDemo.Request;

import br.com.ale.restassuredDemo.Body.CreateAnIssueCopyRequest.Category;
import br.com.ale.restassuredDemo.Body.CreateAnIssueCopyRequest.CreateAnIssueCopyBody;
import br.com.ale.restassuredDemo.Body.CreateAnIssueWithAttachmentsRequest.CreateAnIssueWithAttachmentsBody;
import br.com.ale.restassuredDemo.Body.CreateNewIssueRequest.CreateNewIssueBody;
import br.com.ale.restassuredDemo.Body.IssueCreateAnIssueMinimalRequest.IssueCreateAnIssueMinimalBody;
import br.com.ale.restassuredDemo.bases.RequestRestBase;
import br.com.ale.restassuredDemo.dao.InsertDAO;
import br.com.ale.restassuredDemo.dao.SelectDAO;
import br.com.ale.restassuredDemo.utils.GlobalParameters;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Method;

import javax.swing.*;
import java.util.ArrayList;

import static br.com.ale.restassuredDemo.dao.InsertDAO.INSERIR_UM_PROJETO_MANTISBT_PROJECT_TABLE_FILE;

public class IssuesRequest extends RequestRestBase {
    private final String CREATE_AN_ISSUE_END_POINT = "/api/rest/issues/";

    public IssuesRequest(){
    }

    public void createAnIssueRequest(CreateNewIssueBody createNewIssueBody){
        setEndPoint(CREATE_AN_ISSUE_END_POINT);
        jsonBody = createNewIssueBody;
        method = Method.POST;
        headers.put("Authorization", GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void createAnIssueMinimalRequest(IssueCreateAnIssueMinimalBody issueCreateAnIssueMinimalBody) throws JsonProcessingException {
        setEndPoint(CREATE_AN_ISSUE_END_POINT);
        jsonBody = issueCreateAnIssueMinimalBody;
        method = Method.POST;
        headers.put("Authorization", GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void createAnIssueCopyRequest() throws JsonProcessingException {
        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("CreateAnIssueCopyBody");
        ObjectMapper mapper = new ObjectMapper();
        CreateAnIssueCopyBody createAnIssueCopyBody = mapper.readValue(body, CreateAnIssueCopyBody.class);
        setEndPoint(CREATE_AN_ISSUE_END_POINT);
        jsonBody = createAnIssueCopyBody;
        method = Method.POST;
        headers.put("Authorization", GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void CreateAnIssueWithAttachmentsRequest(CreateAnIssueWithAttachmentsBody createAnIssueWithAttachmentsBody){
        setEndPoint(CREATE_AN_ISSUE_END_POINT);
        jsonBody = createAnIssueWithAttachmentsBody;
        method = Method.POST;
        headers.put("Authorization", GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void getAnIssueRequest(String idIssue){
        setEndPoint(CREATE_AN_ISSUE_END_POINT.concat(idIssue));
        method = Method.GET;
        headers.put("Authorization", GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void getAnIssueSpecificFieldsRequest(Integer idIssue){
        setEndPoint(CREATE_AN_ISSUE_END_POINT.concat(String.valueOf(idIssue)));
        method = Method.GET;
        headers.put("Authorization", GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void getIssuesFilesRequest(String idProject){
        setEndPoint(CREATE_AN_ISSUE_END_POINT.concat(idProject).concat("/files"));
        method = Method.GET;
        headers.put("Authorization", GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void getIssueFileRequest(String idProject, String idFile){
        setEndPoint(CREATE_AN_ISSUE_END_POINT.concat(idProject).concat("/files/").concat(idFile));
        method = Method.GET;
        headers.put("Authorization", GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void getAllIssuesRequest(){
        setEndPoint("/api/rest/issues".concat("?page_size=10").concat("&").concat("page=1"));
        method = Method.GET;
        headers.put("Authorization", GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void getIssuesForAProject(String idProjeto){
        setEndPoint(CREATE_AN_ISSUE_END_POINT.concat("?project_id=").concat(idProjeto));
        //setEndPoint(CREATE_AN_ISSUE_END_POINT.concat("?project_id=").concat(idProjeto).concat("&page_size=10&page=1"));
        method = Method.GET;
        headers.put("Authorization", GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void getIssuesMatchingFilter(String filtro){
        setEndPoint(CREATE_AN_ISSUE_END_POINT.concat("?filter_id=").concat(filtro));
        method = Method.GET;
        headers.put("Authorization", GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }


}
