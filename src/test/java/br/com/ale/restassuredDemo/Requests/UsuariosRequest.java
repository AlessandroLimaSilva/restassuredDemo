package br.com.ale.restassuredDemo.Requests;

import br.com.ale.restassuredDemo.Body.UserRequestBody.AccessLevelPostBody;
import br.com.ale.restassuredDemo.Body.UserRequestBody.UsuariosPostBody;
import br.com.ale.restassuredDemo.Types.UsuariosType;
import br.com.ale.restassuredDemo.Base.RequestRestBase;
import br.com.ale.restassuredDemo.DAO.SelectDAO;
import br.com.ale.restassuredDemo.utils.GlobalParameters;
import io.restassured.http.Method;

public class UsuariosRequest extends RequestRestBase {

    private final String USERS_ME_END_POINT = "/api/rest/users/me";
    private final String USERS_END_POINT = "/api/rest/users/";

    public UsuariosRequest(){}

    public void getMinhasInformacoesDeUsuarioRequest(){
        setEndPoint(USERS_ME_END_POINT);
        method = Method.GET;
        headers.put("Authorization",GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public UsuariosRequest(String nomeUsuario){
        SelectDAO selectDAO = new SelectDAO();
        UsuariosType usuariosType = selectDAO.getUsuarioDadosTesteUsuarioTable(nomeUsuario);
        AccessLevelPostBody access = new AccessLevelPostBody(usuariosType.getTipoCargo());
        UsuariosPostBody user = new UsuariosPostBody(
                usuariosType.getUserName(),
                usuariosType.getPassword(),
                usuariosType.getRealName(),
                usuariosType.getEmail(),
                access,
                usuariosType.getEnableBoolean(),
                usuariosType.getProtektedBoolean());
        setEndPoint(USERS_END_POINT);
        jsonBody = user;
        method = Method.POST;
        headers.put("Authorization",GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void criarNovoUsuarioRequest(String nomeUsuario){
        SelectDAO selectDAO = new SelectDAO();
        UsuariosType usuariosType = selectDAO.getUsuarioDadosTesteUsuarioTable(nomeUsuario);
        AccessLevelPostBody access = new AccessLevelPostBody(usuariosType.getTipoCargo());
        UsuariosPostBody user = new UsuariosPostBody(
                usuariosType.getUserName(),
                usuariosType.getPassword(),
                usuariosType.getRealName(),
                usuariosType.getEmail(),
                access,
                usuariosType.getEnableBoolean(),
                usuariosType.getProtektedBoolean());
        setEndPoint(USERS_END_POINT);
        jsonBody = user;
        method = Method.POST;
        headers.put("Authorization",GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void criarNovoUsuarioMinimoRequest(String nome){
        setEndPoint(USERS_END_POINT);
        jsonBody = "{ \"username\" : \""+nome+"\"}";
        method = Method.POST;
        headers.put("Authorization",GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void CriarNovoUsuarioSemNomeDeUsuarioRequest(String nomeUsuario){
        SelectDAO selectDAO = new SelectDAO();
        UsuariosType usuariosType = selectDAO.getUsuarioDadosTesteUsuarioTable(nomeUsuario);
        usuariosType.setUserName("");
        AccessLevelPostBody access = new AccessLevelPostBody(usuariosType.getTipoCargo());
        UsuariosPostBody user = new UsuariosPostBody(
                usuariosType.getUserName(),
                usuariosType.getPassword(),
                usuariosType.getRealName(),
                usuariosType.getEmail(),
                access,
                usuariosType.getEnableBoolean(),
                usuariosType.getProtektedBoolean());
        setEndPoint(USERS_END_POINT);
        jsonBody = user;
        method = Method.POST;
        headers.put("Authorization",GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void envioRequisicaoComIDDoUsuario(String nome){
        setEndPoint(USERS_END_POINT.concat(String.valueOf(1)));
        method = Method.GET;
        headers.put("Authorization",GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

    public void envioARequisicaoComIDDoUsuarioASerDeletadoRequest(int idUsuario){
        setEndPoint(USERS_END_POINT.concat(String.valueOf(idUsuario)));
        method = Method.DELETE;
        headers.put("Authorization",GlobalParameters.TOKEN);
        headers.put("content-type","application/json");
    }

}
