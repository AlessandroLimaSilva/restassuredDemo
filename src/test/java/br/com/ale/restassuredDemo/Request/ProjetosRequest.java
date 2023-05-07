package br.com.ale.restassuredDemo.Request;

import br.com.ale.restassuredDemo.Body.CreateAProjectPostRequest.CreateAProjectPostBody;
import br.com.ale.restassuredDemo.Body.UpdateAProjectRequest.UpdateAProjectPatchBody;
import br.com.ale.restassuredDemo.bases.RequestRestBase;
import br.com.ale.restassuredDemo.utils.GlobalParameters;
import io.restassured.http.Method;

import java.util.ArrayList;

public class ProjetosRequest extends RequestRestBase {

    private final String PROJECTS_END_POINT = "/api/rest/projects/";

    public ProjetosRequest(){}

    public void todosOsProjetosRequest(){
        setEndPoint(PROJECTS_END_POINT);
        method = Method.GET;
        headers.put("Authorization", GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void deletarProjetoIDRequest(String id){
        setEndPoint(PROJECTS_END_POINT.concat(id));
        method = Method.DELETE;
        headers.put("Authorization", GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void obterProjetoIDRequest(String id){
        setEndPoint(PROJECTS_END_POINT.concat(id));
        method = Method.GET;
        headers.put("Authorization", GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void obterUsuarioProjetoIDRequest(String id){
        setEndPoint(PROJECTS_END_POINT.concat(id).concat("/users"));
        method = Method.GET;
        headers.put("Authorization", GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void criarNovoProjetoRequest(CreateAProjectPostBody createAProjectPostBody){
        setEndPoint(PROJECTS_END_POINT);
        jsonBody = createAProjectPostBody;
        method = Method.POST;
        headers.put("Authorization", GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void alterarNomeDoProjetoRequest(UpdateAProjectPatchBody updateAProjectPatchBody, String idProjeto){
        setEndPoint(PROJECTS_END_POINT.concat(idProjeto));
        jsonBody = updateAProjectPatchBody;
        method = Method.PATCH;
        headers.put("Authorization", GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }


}
