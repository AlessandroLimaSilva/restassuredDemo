package br.com.ale.restassuredDemo.StepDefinitions;

import br.com.ale.restassuredDemo.Requests.UsuariosRequest;
import br.com.ale.restassuredDemo.Types.UsuariosType;
import br.com.ale.restassuredDemo.DAO.DeleteDAO;
import br.com.ale.restassuredDemo.DAO.InsertDAO;
import br.com.ale.restassuredDemo.DAO.SelectDAO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;

import java.sql.SQLException;

import static org.hamcrest.Matchers.*;

public class UsuarioStepDefinitions {
    UsuariosRequest usuariosRequest;
    protected static ValidatableResponse validatableResponse;
    protected static String NOME_USUARIO;
    protected static String EMAIL;
    protected static String NOME_REAL;
    protected static int ID_USUARIO;

    @When("envio a requisicao sobre minhas informacoes de usuario")
    public void envioRequisicaoSobreMinhasInformacoesDeUsuario(){
        usuariosRequest = new UsuariosRequest();
        usuariosRequest.getMinhasInformacoesDeUsuarioRequest();
        validatableResponse = usuariosRequest.executeRequest();
        validatableResponse.log().all();
    }

    @Then("recebo minhas informacoes de usuario com sucesso")
    public void receboMinhasInformacoesDeUsuarioComSucesso(){
        validatableResponse.statusCode(200).body(
                "id", is(1),
                "name", containsString("administrator"),
                "real_name", containsString("admin"),
                "email",containsString("root@localhost"),
                "language", containsString("english"),
                "access_level.id", is(90),
                "access_level.name", containsString("administrator"),
                "access_level.label", containsString("administrator"));
    }

    @And("^que ja tenho um usuario cadastrado (.*)$")
    public void queJaTenhoUmUsuarioCadastrado(String nomeUsuario){
        NOME_USUARIO = nomeUsuario;
        DeleteDAO deleteDao = new DeleteDAO();
        deleteDao.deletarUsuarioPorNomeMantisBTTableUsuario(nomeUsuario);
        SelectDAO selectDAO = new SelectDAO();
        UsuariosType usuariosType = selectDAO.getUsuarioDadosTesteUsuarioTable(nomeUsuario);
        InsertDAO insertDAO = new InsertDAO();
        insertDAO.setInsertUsuarioTableMantisBTDataBase(usuariosType);
    }

    @When("envio a requisicao com o id do usuario")
    public void envioARequisiciaoComIdDoUsuario(){
        usuariosRequest = new UsuariosRequest();
        usuariosRequest.envioRequisicaoComIDDoUsuario(NOME_USUARIO);
        validatableResponse = usuariosRequest.executeRequest();
        validatableResponse.log().all();
    }

    @Then("recebo as informacoes do usuario com sucesso")
    public void receboAsInformacoesDoUsuarioComSucesso(){
        SelectDAO selectDAO = new SelectDAO();
        UsuariosType usuariosType = selectDAO.getUsuarioDadosTesteUsuarioTable(NOME_USUARIO);
        validatableResponse.statusCode(201).body(
                "user.name", containsString(usuariosType.getUserName()),
                "user.real_name", containsString(usuariosType.getRealName()),
                "user.email",containsString(usuariosType.getEmail()));
    }

    @When("^envio um novo usuario do tipo (.*)$")
    public void CriarNovoUsuarioPost(String nomeUsuario){
        NOME_USUARIO = nomeUsuario;
        DeleteDAO deleteDao = new DeleteDAO();
        deleteDao.deletarUsuarioPorNomeMantisBTTableUsuario(NOME_USUARIO);
        usuariosRequest = new UsuariosRequest();
        usuariosRequest.criarNovoUsuarioRequest(nomeUsuario);
        validatableResponse = usuariosRequest.executeRequest();
    }

    @When("^envio um usuario do tipo (.*) ja cadastrado$")
    public void envioUsuarioJaCadastrado(String nomeUsuario){
        NOME_USUARIO = nomeUsuario;
        usuariosRequest = new UsuariosRequest();
        usuariosRequest.criarNovoUsuarioRequest(nomeUsuario);
        validatableResponse = usuariosRequest.executeRequest();
        validatableResponse.log().all();
    }

    @Then("o usuario e criado com sucesso")
    public void usuarioCriadoComSucesso(){
        SelectDAO selectDAO = new SelectDAO();
        UsuariosType usuariosType = selectDAO.getUsuarioDadosTesteUsuarioTable(NOME_USUARIO);
        validatableResponse.statusCode(201).body(
                "user.name", containsString(usuariosType.getUserName()),
                "user.real_name", containsString(usuariosType.getRealName()),
                "user.email",containsString(usuariosType.getEmail()));
        validatableResponse.log().all();
    }

    @And("^ja possuo um usuario com o nome (.*) cadastrado$")
    public void possuoUsuarioComNomeCadastrado(String nome){
        DeleteDAO deleteDao = new DeleteDAO();
        deleteDao.deletarUsuarioPorNomeMantisBTTableUsuario(nome);
        SelectDAO selectDAO = new SelectDAO();
        UsuariosType usuariosType = selectDAO.getUsuarioDadosTesteUsuarioTable(nome);
        InsertDAO insertDAO = new InsertDAO();
        insertDAO.setInsertUsuarioTableMantisBTDataBase(usuariosType);
    }

    @And("^que ja possuo o usuario (.*) cadastrado na aplicacao$")
    public void queJaPossuoOUsuarioCadastradoNaAplicacao(String nomeUsuario) throws SQLException {
        DeleteDAO deleteDao = new DeleteDAO();
        deleteDao.deletarUsuarioPorNomeMantisBTTableUsuario(nomeUsuario);
        SelectDAO selectDAO = new SelectDAO();
        UsuariosType usuariosType = selectDAO.getUsuarioDadosTesteUsuarioTable(nomeUsuario);
        InsertDAO insertDAO = new InsertDAO();
        ID_USUARIO = insertDAO.setInsertUsuarioTableMantisBTDataBaseAndReturnID(usuariosType);
    }

    @And("^possuo um usuario com o email (.*) ja cadastrado$")
    public void possouUsuarioComEmailCadastrado(String nome){
        DeleteDAO deleteDao = new DeleteDAO();
        deleteDao.deletarUsuarioPorNomeMantisBTTableUsuario(nome);
        SelectDAO selectDAO = new SelectDAO();
        UsuariosType usuariosType = selectDAO.getUsuarioDadosTesteUsuarioTable(nome);
        usuariosType.setUserName(usuariosType.getRealName());
        EMAIL = usuariosType.getEmail();
        NOME_REAL = usuariosType.getRealName();
        InsertDAO insertDAO = new InsertDAO();
        insertDAO.setInsertUsuarioTableMantisBTDataBase(usuariosType);
    }

    @When("envio um novo usuario sem o nome de usuario")
    public void envioNovoUsuarioSemNomeDeUsuario(){
        NOME_USUARIO = "administrador";
        usuariosRequest = new UsuariosRequest();
        usuariosRequest.CriarNovoUsuarioSemNomeDeUsuarioRequest(NOME_USUARIO);
        validatableResponse = usuariosRequest.executeRequest();
    }

    @Then("o novo usuario nao e cadastrado com sucesso")
    public void novoUsuarioNaoECadastradoComSucesso(){
        validatableResponse.statusCode(400).body(
                "message",containsString("Invalid username ''")
                );
        validatableResponse.log().all();
    }

    @When("^envio um usuario (.*) com o email ja cadastrado$")
    public void envioUmUsuarioComEmailJaCadastrado(String nome){
        usuariosRequest = new UsuariosRequest();
        usuariosRequest.criarNovoUsuarioRequest(nome);
        validatableResponse = usuariosRequest.executeRequest();
    }

    @Then("o usuario com o nome de usuario ja utilizado nao e cadastrado")
    public void usuarioComNomeDeUsuarioJaUtilizadoNaoECadastrado(){
        validatableResponse.statusCode(400).body(
                "message",containsString("Username '"+NOME_USUARIO+"' already used."),
                "code",is(800),
                "localized", containsString("That username is already being used. Please go back and select another one."));
    }

    @Then("o usuario com email ja utilizado nao e cadastrado")
    public void usuarioComEmailJaUtilizadoNaoECadastrado(){
        validatableResponse.statusCode(400).body(
                "message",containsString("Email '"+EMAIL+"' already used."),
                "code",is(813),
                "localized", containsString("That email is already being used. Please go back and select another one."));
        validatableResponse.log().all();
    }

    @And("o usuario com email ja cadastrado e deletado da aplicacao")
    public void usuarioComEmailJaCadastradoEDeletadoDaAplicacao(){
        DeleteDAO deleteDao = new DeleteDAO();
        deleteDao.deletarUsuarioPorNomeRealMantisBTTableUsuario(NOME_REAL);
    }

    @Then("o schema de criar novo usuario e validado com sucesso")
    public void schemaDoNovoUsuarioValidadoComSucesso(){
        validatableResponse.log().all();
        validatableResponse.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("novoUsuarioSchema.json"));
    }

    @And("o usuario cadastrado e deletado da aplicacao")
    public void deletarUsuarioDaAplicacaoNoBDMantisBTUsuarioTable(){
        DeleteDAO deleteDao = new DeleteDAO();
        deleteDao.deletarUsuarioPorNomeMantisBTTableUsuario(NOME_USUARIO);
    }

    @When("envio um novo usuario minimo")
    public void envioNovoUsuarioMinimo(){
        NOME_USUARIO = "minimo";
        DeleteDAO deleteDao = new DeleteDAO();
        deleteDao.deletarUsuarioPorNomeMantisBTTableUsuario(NOME_USUARIO);
        usuariosRequest = new UsuariosRequest();
        usuariosRequest.criarNovoUsuarioMinimoRequest(NOME_USUARIO);
        validatableResponse = usuariosRequest.executeRequest();
    }

    @When("envio a requisicao com o id do usuario a ser deletado")
    public void envioARequisicaoComIDDoUsuarioASerDeletado(){
        usuariosRequest = new UsuariosRequest();
        usuariosRequest.envioARequisicaoComIDDoUsuarioASerDeletadoRequest(ID_USUARIO);
        validatableResponse = usuariosRequest.executeRequest();
        validatableResponse.log().all();
    }

    @Then("o usuario e deletado com sucesso")
    public void usuarioEDeletadoComSucesso(){
        validatableResponse.statusCode(204);
        validatableResponse.log().all();
    }

    @Then("o usuario minimo e criado com sucesso")
    public void usuarioMinimoCriadoComSucesso(){
        validatableResponse.statusCode(201).body(
                "user.name", containsString(NOME_USUARIO));
    }

    @When("envio um novo usuario minimo sem o nome de usuario")
    public void envioNovoUsuarioMinimoSemNomeDeUsuario(){
        NOME_USUARIO = "";
        usuariosRequest = new UsuariosRequest();
        usuariosRequest.criarNovoUsuarioMinimoRequest(NOME_USUARIO);
        validatableResponse = usuariosRequest.executeRequest();
    }

    @Then("o usuario minimo nao e criado com sucesso")
    public void usuarioMinimoNaoCriadoComSucesso(){
        validatableResponse.statusCode(400).body(
                "message",containsString("Invalid username ''"),
                "code",is(805),
                "localized", containsString("The username is invalid. Usernames may only contain Latin letters, numbers, spaces, hyphens, dots, plus signs and underscores."));
        validatableResponse.log().all();
    }

    @Then("json schema criar novo usuario minimo e validado com sucesso")
    public void jsonSchemaCriarNovoUsuarioMinimoValidadoComSucesso(){
        validatableResponse.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("novoUsuarioMinimoSchema.json"));
    }

    @Then("json schema de obter minhas informacoes de usuario e validado com sucesso")
    public void jsonSchemaObterMinhasInformacoesDeUsuarioValidadoComSucesso(){
        validatableResponse.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("minhasInformacoesInfoSchema.json"));
    }
}
