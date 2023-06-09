package br.com.ale.restassuredDemo.StepDefinitions;

import br.com.ale.restassuredDemo.Body.CreateAProjectPostRequestBody.CreateAProjectPostBody;
import br.com.ale.restassuredDemo.Body.UpdateAProjectRequestBody.UpdateAProjectPatchBody;
import br.com.ale.restassuredDemo.Body.UtilsRequestBody.ProjetoBody;
import br.com.ale.restassuredDemo.DAO.DeleteDAO;
import br.com.ale.restassuredDemo.DAO.InsertDAO;
import br.com.ale.restassuredDemo.DAO.SelectDAO;
import br.com.ale.restassuredDemo.Requests.ProjetosRequest;
import br.com.ale.restassuredDemo.utils.UtilsQuery;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;


public class ProjetosStepDefinitions {

    ProjetosRequest PROJETOS_REQUEST;
    protected static ValidatableResponse validatableResponse;
    protected static ArrayList<Object> ts = new ArrayList<>();
    public static Integer ID_PROJETO;

    @When("uma requisição é enviada para obter todos os projetos")
    public void umaRequisicaoEEnviadaParaObterTodosOsProjetos(){
        PROJETOS_REQUEST = new ProjetosRequest();
        PROJETOS_REQUEST.todosOsProjetosRequest();
        validatableResponse = PROJETOS_REQUEST.executeRequest();
        validatableResponse.log().all();
    }

    @Then("todos os projetos sao retornados com sucesso")
    public void todosOsProjetosSaoretornadosComSucesso() throws Exception {
        SelectDAO selectDAO = new SelectDAO();
        int quantidadeDeProjetosNoBanco = selectDAO.getQuantidadeDeProjetosCadastradosNoMantiBT();
        JsonPath jsonPath = validatableResponse.extract().jsonPath();
        List<Integer> idList =jsonPath.getList("projects");
        validatableResponse.statusCode(200);
        GroovyClassLoader loader = new GroovyClassLoader();
        Class<?> groovyClass = loader.parseClass(new File("src/test/java/br/com/ale/restassuredDemo/utils/ValidacaoGroovy.groovy"));
        GroovyObject groovyObject = (GroovyObject) groovyClass.getDeclaredConstructor().newInstance();
        Object[] objeto = {quantidadeDeProjetosNoBanco, idList.size()};
        boolean numerosIguais = (boolean) groovyObject.invokeMethod("numerosSaoIguais", objeto);
        Assert.assertTrue(numerosIguais);
        validatableResponse.log().all();
    }

    @Then("o status code 200 e retornado com sucesso")
    public void statusCodeHTTPOKERetornadoComSucesso(){
        validatableResponse.statusCode(200);
    }

    @And("que exista um projeto cadastrado")
    public void queExistaUmProjetoCadastrado() throws Exception {
        InsertDAO insertDAO = new InsertDAO();
        ID_PROJETO = insertDAO.setInsertOneProjectAndReturnPk();
    }

    @Then("json schema para obter todos os projetos e validado com sucesso")
    public void jsonSchemaParaObterTodosOsProjetosEValidadoComSucesso(){
        validatableResponse.log().all();
        validatableResponse.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("todosOsProjetosSchema.json"));
    }

    @And("o projeto cadastrado é deletado")
public void projetoCadastradoEDeletado(){
        DeleteDAO deleteDao = new DeleteDAO();
        deleteDao.deleteProjectMantisBTPerIDProject(ID_PROJETO);
    }

    @And("deleto os projetos cadastrados")
    public void deletoOsProjetosCadastrados(){
        SelectDAO selectDAO = new SelectDAO();
        ArrayList<Object> idList = new ArrayList<>();
        ArrayList<String> ls = UtilsQuery.lerSQL(new File("src/test/resources/csv/teste.csv"));
        for(String loop:ls){
            String aux = selectDAO.searchProjectIDPerNameInMantisProjectTable(loop);
            if(!StringUtils.isEmpty(aux)) {
                idList.add(Integer.parseInt(aux));
            }
        }
        for(Object loop: idList){
            PROJETOS_REQUEST = new ProjetosRequest();
            PROJETOS_REQUEST.deletarProjetoIDRequest(loop.toString());
            validatableResponse = PROJETOS_REQUEST.executeRequest();
            validatableResponse.statusCode(200);
        }
    }

    @When("uma requisição é enviada com o id do projeto a ser excluido")
    public void umaRequisicaoEEnviadaComOIDDoProjetoASerExcluido(){
        PROJETOS_REQUEST = new ProjetosRequest();
        PROJETOS_REQUEST.deletarProjetoIDRequest(String.valueOf(ID_PROJETO));
        validatableResponse = PROJETOS_REQUEST.executeRequest();
    }

    @When("^uma requisição para excluir um projeto é enviada informando o id (.*) invalido$")
    public void umaRequisicaoParaExcluirUmProjetoEEnviadaInformandoOIDInvalido(String idProjeto){
        PROJETOS_REQUEST = new ProjetosRequest();
        PROJETOS_REQUEST.deletarProjetoIDRequest(String.valueOf(idProjeto));
        validatableResponse = PROJETOS_REQUEST.executeRequest();
        validatableResponse.log().all();
    }

    @When("uma requisição é enviada com o id {int} de um projeto nao cadastrado")
    public void umaRequisicaoEEnviadaComOIDDeUmProjetoNaoCadastrado(int idProjeto){
        PROJETOS_REQUEST = new ProjetosRequest();
        PROJETOS_REQUEST.deletarProjetoIDRequest(String.valueOf(idProjeto));
        validatableResponse = PROJETOS_REQUEST.executeRequest();
        validatableResponse.log().all();
    }

    @Then("e retornado que nao existe um projeto com esse id com sucesso")
    public void retornadoQuenaoExisteUmProjetoComEsseIDComSucesso() {
        String retorno =  Arrays.toString(validatableResponse.extract().statusLine().split(" "));
        try {
            String[] list = new String[]{Arrays.toString(validatableResponse.extract().statusLine().split(" "))};
            boolean res = false;
            for (String loop : list) {
                if (loop.contains("404")) {
                    res = true;
                    break;
                }
            }
            Assert.assertTrue(res);
        } catch (AssertionError as) {
            throw new AssertionError("Status code expected <404> response as : " + retorno);
        }
        validatableResponse.log().all();
    }

    @Then("^e retornado status code (.*) de requisicao invalida em excluir projeto com sucesso$")
    public void retornadoStatusCodeDeRequisicaoInvalidaEmExcluirProjetoComSucesso(String statusCode){
        String retorno =  Arrays.toString(validatableResponse.extract().statusLine().split(" "));
        try {
            String[] list = new String[]{Arrays.toString(validatableResponse.extract().statusLine().split(" "))};
            boolean res = false;
            for (String loop : list) {
                if (loop.contains(statusCode)) {
                    res = true;
                    break;
                }
            }
            Assert.assertTrue(res);
        } catch (AssertionError as) {
            throw new AssertionError("Status code expected <"+statusCode+"> response as : ".concat(retorno));
        }
    }

    @And("deleto o projeto cadastrado")
    public void deletoOProjetoCadastrado(){
        ArrayList<String> ls = UtilsQuery.lerSQL(new File("src/test/resources/csv/teste.csv"));
        SelectDAO selectDAO = new SelectDAO();
        String aux = selectDAO.searchProjectIDPerNameInMantisProjectTable(ls.get(0));
        PROJETOS_REQUEST = new ProjetosRequest();
        PROJETOS_REQUEST.deletarProjetoIDRequest(aux);
        validatableResponse = PROJETOS_REQUEST.executeRequest();
    }

    @When("uma requisição é enviada com o id do projeto a ser pesquisado")
    public void umaRequisicaoEEnviadaComOIDDoProjetoASerPesquisado(){
        PROJETOS_REQUEST = new ProjetosRequest();
        PROJETOS_REQUEST.obterProjetoIDRequest(String.valueOf(ID_PROJETO));
        validatableResponse = PROJETOS_REQUEST.executeRequest();
        validatableResponse.log().all();
    }

    @When("uma requisição é enviada com o id {int} de um projeto que nao existe")
    public void umaRequisicaoEEnviadaComOIDDeUmProjetoQueNaoExiste(int idProjeto){
        ID_PROJETO = idProjeto;
        PROJETOS_REQUEST = new ProjetosRequest();
        PROJETOS_REQUEST.obterProjetoIDRequest(String.valueOf(idProjeto));
        validatableResponse = PROJETOS_REQUEST.executeRequest();
        validatableResponse.log().all();
    }

    @When("envio a requisicao com o id do projeto do usuario")
    public void envioARequisicaoComIDDoProjetoDoUsuario(){
        ArrayList<String> ls = UtilsQuery.lerSQL(new File("src/test/resources/csv/teste.csv"));
        SelectDAO selectDAO = new SelectDAO();
        String aux = selectDAO.searchProjectIDPerNameInMantisProjectTable(ls.get(0));
        PROJETOS_REQUEST = new ProjetosRequest();
        PROJETOS_REQUEST.obterUsuarioProjetoIDRequest(aux);
        validatableResponse = PROJETOS_REQUEST.executeRequest();
        validatableResponse.log().all();
    }

    @Then("o usuario e retornado com sucesso")
    public void usuarioRetornadoComSucesso(){
        validatableResponse.statusCode(200);
        validatableResponse.log().all();
    }

    @Then("json schema para obter um projeto e validado com sucesso")
    public void projetoRetornadoComSucesso(){
        validatableResponse.statusCode(200).body(JsonSchemaValidator.matchesJsonSchemaInClasspath("projetoSchema.json"));
        validatableResponse.log().all();
    }

    @Then("o projeto e retornado com sucesso")
    public void projetoERetornadoComSucesso() throws JsonProcessingException {
        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("ProjetoBody");
        ObjectMapper mapper = new ObjectMapper();
        ProjetoBody projetoBody = mapper.readValue(body, ProjetoBody.class);
        validatableResponse.statusCode(200).body(
                "projects.id[0]", is(ID_PROJETO),
                "projects.name[0]", containsString(projetoBody.getName()),
                "projects.status.id[0]", is(projetoBody.getStatus()),
                "projects.description[0]", containsString(projetoBody.getDescription()));
        validatableResponse.log().all();
    }

    @Then("e retornado com sucesso que o projeto nao existe")
    public void retornardoComSucessoQueOProjetoNaoExiste(){
        validatableResponse.statusCode(404).body(
                "message",containsString("Project #".concat(String.valueOf(ID_PROJETO)).concat(" not found")),
                "localized",containsString("Project \"".concat(String.valueOf(ID_PROJETO)).concat("\" not found"))
                );
    }


    @Then("o projeto e deletado com sucesso")
    public void projetoDeletadoComSucesso(){
        validatableResponse.statusCode(200);
        validatableResponse.log().all();
    }

    @And("a miserable little pile of secrets")
    public void aMiserableLittlePileOfSecrets(){
        InsertDAO insertDAO = new InsertDAO();
        insertDAO.popularBancoDeDadoTesteAPI();
    }

    @Then("Dracula Conde")
    public void DraculaConde(){
        System.out.println("Banco de dados online e populado com sucesso !!!");
    }

    @When("consulto todos os id's dos projetos")
    public void consultoTodosOsIDSDosProjetos(){
        SelectDAO selectDAO = new SelectDAO();
        ArrayList<String> ls = UtilsQuery.lerSQL(new File("src/test/resources/csv/teste.csv"));
        for(String loop:ls){
            String aux = selectDAO.searchProjectIDPerNameInMantisProjectTable(loop);
            if(!StringUtils.isEmpty(aux)) {
                ts.add(Integer.parseInt(aux));
            }
        }
    }

    @Then("os ids sao retornados com sucesso")
    public void idsRetornadosComSucesso(){
        for(Object loop: ts){
            PROJETOS_REQUEST = new ProjetosRequest();
            PROJETOS_REQUEST.deletarProjetoIDRequest(loop.toString());
            validatableResponse = PROJETOS_REQUEST.executeRequest();
            validatableResponse.log().all();
        }
    }

    @When("uma requisição é enviada com os dados necessarios para criar um novo projeto")
    public void umaRequisicaoEEnviadaComOsDadosNecessariosParaCriarUmNovoProjeto() throws JsonProcessingException {
        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("CreateAProjectPostBody");
        ObjectMapper mapper = new ObjectMapper();
        CreateAProjectPostBody createAProjectPostBody = mapper.readValue(body, CreateAProjectPostBody.class);
        PROJETOS_REQUEST = new ProjetosRequest();
        PROJETOS_REQUEST.criarNovoProjetoRequest(createAProjectPostBody);
        validatableResponse = PROJETOS_REQUEST.executeRequest();
        validatableResponse.log().all();
    }

    @Then("o projeto e criado com sucesso")
    public void oProjetoECriadoComSucesso() throws JsonProcessingException {
        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("CreateAProjectPostBody");
        ObjectMapper mapper = new ObjectMapper();
        CreateAProjectPostBody createAProjectPostBody = mapper.readValue(body, CreateAProjectPostBody.class);
        validatableResponse.statusCode(201).body(
                "project.name", containsString(createAProjectPostBody.getName()),
                "project.status.name", containsString(createAProjectPostBody.getStatus().getName()),
                "project.description", containsString(createAProjectPostBody.getDescription()),
                "project.enabled", is(createAProjectPostBody.getEnabled())
        );
        JsonPath jsonPath = validatableResponse.extract().jsonPath();
        ID_PROJETO = jsonPath.get("project.id");
        validatableResponse.log().all();
    }

    @When("uma requisição é enviada com os dados para alterar o projeto")
    public void umaRequisicaoEEnviadaComOsDadosParaAlterarOProjeto() throws JsonProcessingException {
        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("UpdateAProjectPatchBody");
        ObjectMapper mapper = new ObjectMapper();
        UpdateAProjectPatchBody updateAProjectPatchBody = mapper.readValue(body, UpdateAProjectPatchBody.class);
        updateAProjectPatchBody.setID(ID_PROJETO);
        PROJETOS_REQUEST = new ProjetosRequest();
        PROJETOS_REQUEST.alterarNomeDoProjetoRequest(updateAProjectPatchBody, String.valueOf(ID_PROJETO));
        validatableResponse = PROJETOS_REQUEST.executeRequest();
        validatableResponse.log().all();
    }

    @When("uma requisição é enviada informando o id {int} de um projeto que nao existe")
    public void umaRequisicaoEEnviadaInformandoOIDDeUmProjetoQueNaoExiste(int idProjeto) throws JsonProcessingException {
        ID_PROJETO = idProjeto;
        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("UpdateAProjectPatchBody");
        ObjectMapper mapper = new ObjectMapper();
        UpdateAProjectPatchBody updateAProjectPatchBody = mapper.readValue(body, UpdateAProjectPatchBody.class);
        updateAProjectPatchBody.setID(idProjeto);
        PROJETOS_REQUEST = new ProjetosRequest();
        PROJETOS_REQUEST.alterarNomeDoProjetoRequest(updateAProjectPatchBody, String.valueOf(idProjeto));
        validatableResponse = PROJETOS_REQUEST.executeRequest();
        validatableResponse.log().all();
    }

    @When("^uma requisição para alterar um projeto é enviada informando o id (.*) invalido$")
    public void umaRequisicaoParaAlterarUmProjetoEEnviadaInformandoOIDInvalido(String idProjeto) throws JsonProcessingException {
        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("UpdateAProjectPatchBody");
        ObjectMapper mapper = new ObjectMapper();
        UpdateAProjectPatchBody updateAProjectPatchBody = mapper.readValue(body, UpdateAProjectPatchBody.class);
        PROJETOS_REQUEST = new ProjetosRequest();
        PROJETOS_REQUEST.alterarNomeDoProjetoRequest(updateAProjectPatchBody, idProjeto);
        validatableResponse = PROJETOS_REQUEST.executeRequest();
        validatableResponse.log().all();
    }

    @Then("^e retornado o status code (.*) com sucesso$")
    public void retornadoOStatusCodeComSucesso(int statusCode){
        validatableResponse.statusCode(statusCode);
        validatableResponse.log().all();
    }

    @Then("o projeto e alterado com sucesso")
    public void projetoEAlteradoComSucesso() throws JsonProcessingException {
        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("UpdateAProjectPatchBody");
        ObjectMapper mapper = new ObjectMapper();
        UpdateAProjectPatchBody updateAProjectPatchBody = mapper.readValue(body, UpdateAProjectPatchBody.class);
        validatableResponse.statusCode(200).body(
                "project.id", is(ID_PROJETO),
                "project.name", containsString(updateAProjectPatchBody.getName()),
                "project.enabled", is(updateAProjectPatchBody.getEnabled()),
                "project.inherit_global", is(Integer.parseInt(String.valueOf(updateAProjectPatchBody.getInheritGlobal()))));
        validatableResponse.log().all();
    }

}
