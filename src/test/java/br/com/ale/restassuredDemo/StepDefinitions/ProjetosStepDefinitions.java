package br.com.ale.restassuredDemo.StepDefinitions;

import br.com.ale.restassuredDemo.Body.CreateAProjectPostRequestBody.CreateAProjectPostBody;
import br.com.ale.restassuredDemo.Body.UpdateAProjectRequestBody.UpdateAProjectPatchBody;
import br.com.ale.restassuredDemo.Body.UtilsRequestBody.ProjetoBody;
import br.com.ale.restassuredDemo.DAO.DeleteDao;
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

    @When("envio a requisicao para obter todos os projetos")
    public void envioARequisicaoParaObterTodosOsProjetos(){
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
        Class<?> groovyClass = loader.parseClass(new File("src/test/java/br/com/ale/restassuredDemo/utils/ValidacaoRegex.groovy"));

        GroovyObject groovyObject = (GroovyObject) groovyClass.getDeclaredConstructor().newInstance();
        Object[] objeto = {quantidadeDeProjetosNoBanco, idList.size()};
        boolean numerosIguais = (boolean) groovyObject.invokeMethod("numerosSaoIguais", objeto);
        Assert.assertTrue(numerosIguais);
        //Assert.assertEquals(quantidadeDeProjetosNoBanco,idList.size());
        validatableResponse.log().all();
    }

    @Then("o status code 200 e validado com sucesso")
    public void statusCodeHTTPOKEValidadoComSucesso(){
        validatableResponse.statusCode(200);
    }

    @And("que possuo um projeto cadastrado")
    public void quePossuoUmProjetoCadastrado() throws Exception {
        InsertDAO insertDAO = new InsertDAO();
        ID_PROJETO = insertDAO.setInsertOneProjectAndReturnPk();
    }

    @Then("json schema para obter todos os projetos e validado com sucesso")
    public void jsonSchemaParaObterTodosOsProjetosEValidadoComSucesso(){
        validatableResponse.log().all();
        validatableResponse.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("todosOsProjetosSchema.json"));
    }

    @And("deleto o projeto que foi cadastrado")
public void deletoOProjetoQueFoiCadastrado(){
        DeleteDao deleteDao = new DeleteDao();
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

    @When("envio a requisicao com o id do projeto a ser excluido")
    public void enviarRequisicaoComIdDoProjetoASerExcluido(){
        PROJETOS_REQUEST = new ProjetosRequest();
        PROJETOS_REQUEST.deletarProjetoIDRequest(String.valueOf(ID_PROJETO));
        validatableResponse = PROJETOS_REQUEST.executeRequest();
    }

    @When("^informo id (.*) invalido na requisicao de excluir um projeto$")
    public void informoIDInvalidoNaRequisicaoDeExcluirUmProjeto(String idProjeto){
        PROJETOS_REQUEST = new ProjetosRequest();
        PROJETOS_REQUEST.deletarProjetoIDRequest(String.valueOf(idProjeto));
        validatableResponse = PROJETOS_REQUEST.executeRequest();
        validatableResponse.log().all();
    }

    @When("informo o id {int} de um projeto nao cadastrado na requisicao")
    public void informoOIDDeUmProjetoNaoCadastradoNaRequisicao(int idProjeto){
        PROJETOS_REQUEST = new ProjetosRequest();
        PROJETOS_REQUEST.deletarProjetoIDRequest(String.valueOf(idProjeto));
        validatableResponse = PROJETOS_REQUEST.executeRequest();
        validatableResponse.log().all();
    }

    @Then("e informado que nao existe um projeto com esse id com sucesso")
    public void informadoQuenaoExisteUmProjetoComEsseIDComSucesso() {
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

    @When("envio a requisicao com o id do projeto ser pesquisado")
    public void enviarRequisicaoComIdDoProjetoASerPesquisado(){
        PROJETOS_REQUEST = new ProjetosRequest();
        PROJETOS_REQUEST.obterProjetoIDRequest(String.valueOf(ID_PROJETO));
        validatableResponse = PROJETOS_REQUEST.executeRequest();
        validatableResponse.log().all();
    }

    @When("que envio o id {int} de um projeto que nao existe")
    public void queEnvioOIDDeUmProjetoQueNaoExiste(int idProjeto){
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

    @And("monto o banco de dados do teste")
    public void montarBancoDeDadosDoTeste() throws Exception {
        InsertDAO insertDAO = new InsertDAO();
        insertDAO.popularBancoDeDadoTesteAPI();
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

    @When("informo os dados necessarios para criar um novo projeto na requisicao")
    public void informoOsDadosNecessariosParaCriarUmNovoProjetoNaRequisicao() throws JsonProcessingException {
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

    @When("informo os dados para alterar o projeto")
    public void informoOsDadosParaAlterarONomeDoProjetoPara() throws JsonProcessingException {
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

    @When("informo na requisicao um id {int} de projeto que nao existe")
    public void informoNaRequisicaoUmIDDeProjetoQueNaoExiste(int idProjeto) throws JsonProcessingException {
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

    @When("^informo o id (.*) invalido na requisicao alterar um projeto$")
    public void informoOIDInvalidoNaRequisicaoAlterarUmProjeto(String idProjeto) throws JsonProcessingException {
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
