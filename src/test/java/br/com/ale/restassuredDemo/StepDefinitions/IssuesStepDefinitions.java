package br.com.ale.restassuredDemo.StepDefinitions;

import br.com.ale.restassuredDemo.Body.CreateAnIssueCopyRequestBody.CreateAnIssueCopyBody;
import br.com.ale.restassuredDemo.Body.CreateAnIssueWithAttachmentsRequestBody.*;
import br.com.ale.restassuredDemo.Body.CreateNewIssueRequestBody.Category;
import br.com.ale.restassuredDemo.Body.CreateNewIssueRequestBody.CreateNewIssueBody;
import br.com.ale.restassuredDemo.Body.IssueCreateAnIssueMinimalRequestBody.CreateBugTextTableBody;
import br.com.ale.restassuredDemo.Body.IssueCreateAnIssueMinimalRequestBody.IssueCreateAnIssueMinimalBody;
import br.com.ale.restassuredDemo.Body.UtilsRequestBody.UsuarioTestBody;
import br.com.ale.restassuredDemo.Requests.IssuesRequest;
import br.com.ale.restassuredDemo.DAO.DeleteDAO;
import br.com.ale.restassuredDemo.DAO.InsertDAO;
import br.com.ale.restassuredDemo.DAO.SelectDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;

public class IssuesStepDefinitions {
    private static final Logger LOGGER = LoggerFactory.getLogger(IssuesStepDefinitions.class);
    IssuesRequest issuesRequest;

    protected static ValidatableResponse validatableResponse;
    public static Integer ID_PROJETO;
    public static String ID_NOME;
    public static int ID_TAREFA;
    public static int ID_BUGTEXT;
    public static int ID_FILEONE;
    public static int ID_FILETWO;

    @Given("que exista um novo projeto cadastrado")
    public void queExistaUmNovoProjetoCadastrado() throws Exception {
        InsertDAO insertDAO = new InsertDAO();
        ID_PROJETO = insertDAO.setInsertOneProjectAndReturnPk();
    }

    @When("uma requisição e enviada para criar uma nova tarefa")
    public void umaRequisicaoEEnviadaParaCriarUmaNovaTarefa() throws JsonProcessingException {
        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("CreateNewIssueBody");
        ObjectMapper mapper = new ObjectMapper();
        CreateNewIssueBody createNewIssueBody = mapper.readValue(body, CreateNewIssueBody.class);
        createNewIssueBody.setCustomFields(null);
        Category category = new Category();
        category.setID(ID_PROJETO);
        category.setName(ID_NOME);
        createNewIssueBody.setProject(category);
        issuesRequest = new IssuesRequest();
        issuesRequest.createAnIssueRequest(createNewIssueBody);
        validatableResponse = issuesRequest.executeRequest();
    }

    @Then("a nova tarefa e criada com sucesso")
    public void novaTarefaCriadaComSucesso() throws JsonProcessingException {

        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("CreateNewIssueBody");
        ObjectMapper mapper = new ObjectMapper();
        CreateNewIssueBody createNewIssueBody = mapper.readValue(body, CreateNewIssueBody.class);
        validatableResponse.statusCode(201).body(
                "issue.summary", containsString(createNewIssueBody.getSummary()),
                "issue.description", containsString(createNewIssueBody.getDescription()),
                "issue.additional_information", containsString(createNewIssueBody.getAdditionalInformation()),
                "issue.project.id", is(Integer.parseInt(String.valueOf(ID_PROJETO))),
                "issue.project.name", containsString(createNewIssueBody.getProject().getName()),
                "issue.category.id", is(Integer.parseInt(String.valueOf(createNewIssueBody.getCategory().getID()))),
                "issue.category.name", containsString(createNewIssueBody.getCategory().getName()),
                "issue.view_state.id", is(Integer.parseInt(String.valueOf(createNewIssueBody.getViewState().getID()))),
                "issue.view_state.name", containsString(createNewIssueBody.getViewState().getName()),
                "issue.priority.name", containsString(createNewIssueBody.getPriority().getName()),
                "issue.severity.name", containsString(createNewIssueBody.getSeverity().getName()),
                "issue.reproducibility.name", containsString(createNewIssueBody.getReproducibility().getName()));

        JsonPath jsonPath = validatableResponse.extract().jsonPath();
        ID_TAREFA = jsonPath.get("issue.id");
    }

    @And("os dados da tarefa inseridos sao deletados")
    public void osDadosDaTarefaInseridosSaoDeletados(){
        DeleteDAO deleteDao = new DeleteDAO();
        deleteDao.deleteProjectMantisBTPerIDProject(ID_TAREFA);
    }

    @Then("o json schema ao criar uma nova tarefa e validado com sucesso")
    public void oJsonSchemaAoCriarUmaNovaTarefaEValidadoComSucesso(){
        validatableResponse.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CreateNewIssueSchema.json"));
        JsonPath jsonPath = validatableResponse.extract().jsonPath();
        ID_TAREFA = jsonPath.get("issue.id");
    }

    @Then("o json schema de criar uma nova tarefa com dados minimos e validado com sucesso")
    public void oJsonSchemaDeCriarUmaNovaTarefaComDadosMinimosEValidadoComSucesso(){
        validatableResponse.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("IssueCreateAnIssueMinimalSchema.json"));
        JsonPath jsonPath = validatableResponse.extract().jsonPath();
        ID_TAREFA = jsonPath.get("issue.id");
    }

    @When("uma requisição e enviada informando apenas os dados minimos para criar uma nova tarefa")
    public void umaRequisicaoEEnviadaInformandoApenasOsDadosMinimosParaCriarUmaNovaTarefa() throws JsonProcessingException {
        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("IssueCreateAnIssueMinimalBody");
        ObjectMapper mapper = new ObjectMapper();
        IssueCreateAnIssueMinimalBody issueCreateAnIssueMinimalBody = mapper.readValue(body, IssueCreateAnIssueMinimalBody.class);
        Project project = new Project();
        project.setID(Integer.parseInt(String.valueOf(ID_PROJETO)));
        issueCreateAnIssueMinimalBody.setProject(project);
        issuesRequest = new IssuesRequest();
        issuesRequest.createAnIssueMinimalRequest(issueCreateAnIssueMinimalBody);
        validatableResponse = issuesRequest.executeRequest();
    }

    @Then("a nova tarefa com dados minimos e criada com sucesso")
    public void novaTarefaComDadosMinimosECriadaComSucesso() throws JsonProcessingException {
        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("IssueCreateAnIssueMinimalBody");
        ObjectMapper mapper = new ObjectMapper();
        IssueCreateAnIssueMinimalBody issueCreateAnIssueMinimalBody = mapper.readValue(body, IssueCreateAnIssueMinimalBody.class);
        validatableResponse.statusCode(201).body(
                "issue.summary", containsString(issueCreateAnIssueMinimalBody.getSummary()),
                "issue.description", containsString(issueCreateAnIssueMinimalBody.getDescription()),
                "issue.category.name", containsString(issueCreateAnIssueMinimalBody.getCategory().getName()),
                "issue.project.id", is(Integer.parseInt(String.valueOf(ID_PROJETO))));
        JsonPath jsonPath = validatableResponse.extract().jsonPath();
        ID_TAREFA = jsonPath.get("issue.id");
        validatableResponse.log().all();
    }

    @When("uma requisição e enviada para criar uma nova tarefa de copia")
    public void umaRequisicaoEEnviadaParaCriarUmaNovaTarefaDeCopia() throws JsonProcessingException {
        issuesRequest = new IssuesRequest();
        issuesRequest.createAnIssueCopyRequest();
        validatableResponse = issuesRequest.executeRequest();
    }

    @Then("a nova tarefa de copia e criada com sucesso")
    public void novaTarefaDeCopiaECriadaComSucesso() throws JsonProcessingException {
        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("CreateAnIssueCopyBody");
        ObjectMapper mapper = new ObjectMapper();
        CreateAnIssueCopyBody createAnIssueCopyBody = mapper.readValue(body, CreateAnIssueCopyBody.class);
        validatableResponse.statusCode(201).body(
                "issue.summary", containsString(createAnIssueCopyBody.getSummary()),
                "issue.description", containsString(createAnIssueCopyBody.getDescription()),
                "issue.project.name", containsString(createAnIssueCopyBody.getProject().getName()),
                "issue.category.name", containsString(createAnIssueCopyBody.getCategory().getName())
                );
        validatableResponse.log().all();
        JsonPath jsonPath = validatableResponse.extract().jsonPath();
        ID_TAREFA = jsonPath.get("issue.id");
    }

    @Then("o json schema de criar uma nova tarefa de copia e validado com sucesso")
    public void oJsonSchemaDeCriarUmaNovaTarefaDeCopiaEValidadoComSucesso(){
        validatableResponse.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CreateAnIssueCopySchema.json"));
        JsonPath jsonPath = validatableResponse.extract().jsonPath();
        ID_TAREFA = jsonPath.get("issue.id");
    }

    @Then("o schema de criar uma nova tarefa com arquivos e validado com sucesso")
    public void schemaDeCriarUmaNovaTarefaComArquivosEValidadoComSucesso(){
        validatableResponse.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("createAnIssueWithAttachmentsSchema.json"));
        JsonPath jsonPath = validatableResponse.extract().jsonPath();
        ID_TAREFA = jsonPath.get("issue.id");
    }

    @When("uma requisição e enviada para criar uma nova tarefa com arquivos")
    public void umaRequisicaoEEnviadaParaCriarUmaNovaTarefaComArquivos() throws JsonProcessingException {
        InsertDAO insertDAO = new InsertDAO();
        CustomField customField = new CustomField();
        Field field = insertDAO.setInsertOneCustomFieldTable();
        customField.setField(field);
        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("CreateAnIssueWithAttachmentsBody");
        ObjectMapper mapper = new ObjectMapper();
        CreateAnIssueWithAttachmentsBody createAnIssueWithAttachmentsBody = mapper.readValue(body, CreateAnIssueWithAttachmentsBody.class);
        Project project = new Project();
        project.setID(ID_PROJETO);
        project.setName(createAnIssueWithAttachmentsBody.getProject().getName());
        CustomField[] customFields = createAnIssueWithAttachmentsBody.getCustomFields();
        customField.setValue(customFields[0].getValue());
        customFields[0] = customField;
        createAnIssueWithAttachmentsBody.setProject(project);
        createAnIssueWithAttachmentsBody.setCustomFields(customFields);
        issuesRequest = new IssuesRequest();
        issuesRequest.CreateAnIssueWithAttachmentsRequest(createAnIssueWithAttachmentsBody);
        validatableResponse = issuesRequest.executeRequest();
        validatableResponse.log().all();
    }

    @Then("a nova tarefa com arquivos e validada com sucesso")
    public void novaTarefaComarquivosEValidadaComSucesso() throws JsonProcessingException {
        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("CreateAnIssueWithAttachmentsBody");
        ObjectMapper mapper = new ObjectMapper();
        CreateAnIssueWithAttachmentsBody createAnIssueWithAttachmentsBody = mapper.readValue(body, CreateAnIssueWithAttachmentsBody.class);
        File[] files = createAnIssueWithAttachmentsBody.getFiles();
        validatableResponse.statusCode(201).body(
                "issue.summary", containsString(createAnIssueWithAttachmentsBody.getSummary()),
                "issue.description", containsString(createAnIssueWithAttachmentsBody.getDescription()),
                "issue.project.id", is(Integer.parseInt(String.valueOf(ID_PROJETO))),
                "issue.category.id", is(Integer.parseInt(String.valueOf(createAnIssueWithAttachmentsBody.getCategory().getID()))),
                "issue.category.name", containsString(createAnIssueWithAttachmentsBody.getCategory().getName()),
                "issue.attachments[0].filename", containsString(files[0].getName()),
                "issue.attachments[1].filename", containsString(files[1].getName()));
        JsonPath jsonPath = validatableResponse.extract().jsonPath();
        ID_TAREFA = jsonPath.get("issue.id");
        validatableResponse.log().all();
    }

    @When("uma requisição é enviada com o id da tarefa")
    public void umaRequisicaoEEnviadaComOIDDaTarefa(){
        issuesRequest = new IssuesRequest();
        issuesRequest.getAnIssueRequest(String.valueOf(ID_TAREFA));
        validatableResponse = issuesRequest.executeRequest();
        validatableResponse.log().all();
    }

    @When("^uma requisição é enviada com o id (.*) de uma tarefa que nao existe$")
    public void umaRequisicaoEEnviadaComOIDDeUmaTarefaQueNaoExiste(int idTarefa){
        issuesRequest = new IssuesRequest();
        issuesRequest.getAnIssueRequest(String.valueOf(idTarefa));
        validatableResponse = issuesRequest.executeRequest();
        validatableResponse.log().all();
    }

    @Then("^é retornado que a tarefa com id (.*) invalido não foi encontrado com sucesso$")
    public void tarefaNaoERetornadaComSucesso(String idTarefa){
        validatableResponse.statusCode(404).body(
                "message", containsString("Issue #".concat(idTarefa).concat(" not found")),
                "code", is(1100),
                "localized", containsString("Issue ".concat(idTarefa).concat(" not found")));
        validatableResponse.log().all();
    }

    @When("^uma requisição para obter uma tarefa é enviada informando um dado (.*) invalido$")
    public void umaRequisicaoParaObterUmaTarefaEEnviadaInformandoUmDadoInvalido(String dado){
        issuesRequest = new IssuesRequest();
        issuesRequest.getAnIssueRequest(dado);
        validatableResponse = issuesRequest.executeRequest();
        validatableResponse.log().all();
    }

    @When("envio uma requisicao com campos especificos")
    public void envioUmarequisicaoComCamposEspecificos(){
        issuesRequest = new IssuesRequest();
        issuesRequest.getAnIssueSpecificFieldsRequest(ID_TAREFA);
        validatableResponse = issuesRequest.executeRequest();
        validatableResponse.log().all();
    }

    @Then("a tarefa com campos especificos e retornada com sucesso")
    public void tarefaComcamposEspecificosRetornadaComSucesso(){
        validatableResponse.log().all();
    }

    @Then("a tarefa e retornada com sucesso")
    public void aTarefaRetornadaComSucesso() throws JsonProcessingException {
        SelectDAO selectDAO = new SelectDAO();
        UsuarioTestBody usuarioTestBody = selectDAO.getUsuarioTestBodyMantisBTTableUsuario();
        String budTextBody = selectDAO.selectRequestJsonBody("CreateBugTextTableBody");
        ObjectMapper mapperBug = new ObjectMapper();
        CreateBugTextTableBody createBugTextTableBody = mapperBug.readValue(budTextBody, CreateBugTextTableBody.class);
        String body = selectDAO.selectRequestJsonBody("CreateNewIssueBody");
        ObjectMapper mapper = new ObjectMapper();
        CreateNewIssueBody createNewIssueBody = mapper.readValue(body, CreateNewIssueBody.class);
        validatableResponse.statusCode(200).body(
                "issues.summary[0]", containsString(createNewIssueBody.getSummary()),
                "issues.description[0]", containsString(createBugTextTableBody.getDescription()),
                "issues.additional_information[0]", containsString(createBugTextTableBody.getAdditionalInformation()),
                "issues.project.id[0]", is(Integer.parseInt(String.valueOf(ID_PROJETO))),
                "issues.project.name[0]", containsString(createNewIssueBody.getProject().getName()),
                "issues.reporter.id[0]", is(usuarioTestBody.getId()),
                "issues.reporter.name[0]", containsString(usuarioTestBody.getUsername()),
                "issues.reporter.real_name[0]", containsString(usuarioTestBody.getRealname()),
                "issues.reporter.email[0]", containsString(usuarioTestBody.getEmail()),
                "issues.category.id[0]", is(Integer.parseInt(String.valueOf(createNewIssueBody.getCategory().getID()))),
                "issues.category.name[0]", containsString(createNewIssueBody.getCategory().getName()),
                "issues.view_state.id[0]", is(Integer.parseInt(String.valueOf(createNewIssueBody.getViewState().getID()))),
                "issues.view_state.name[0]", containsString(createNewIssueBody.getViewState().getName()),
                "issues.priority.name[0]", containsString(createNewIssueBody.getPriority().getName()),
                "issues.severity.name[0]", containsString(createNewIssueBody.getSeverity().getName()),
                "issues.reproducibility.name[0]", containsString(createNewIssueBody.getReproducibility().getName()));
        validatableResponse.log().all();
    }

    @And("que exista uma tarefa cadastrada")
    public void queExistaUmaTarefaCadastrada() throws Exception {
        InsertDAO insertDAO = new InsertDAO();
        ArrayList<Integer> list = insertDAO.setInsertBugTextAndProjectAndIssueCreateNewIssueReturnPK();
        ID_BUGTEXT = list.get(0);
        ID_PROJETO = list.get(1);
        ID_TAREFA = list.get(2);
    }

    @And("que exista uma tarefa cadastrada que nao contem arquivos")
    public void queExistaUmaTarefaCadastradaQueNaoContemArquivos() throws Exception {
        queExistaUmaTarefaCadastrada();
    }

    @And("uma requisição com o id de uma tarefa que nao contem arquivo é enviada")
    public void umaRequisicaoComOIDDeUmaTarefaQueNaoContemArquivoEEnviada(){
        issuesRequest = new IssuesRequest();
        issuesRequest.getIssuesFilesRequest(String.valueOf(ID_TAREFA));
        validatableResponse = issuesRequest.executeRequest();
    }

    @When("^uma requisicao de obter uma tarefa com arquivos cadastrados com id (.*) invalido é enviada$")
    public void umaRequisicaoDeObterUmaTarefaComArquivosCadastradosComIDInvalidoEEnviada(String idTarefa){
        issuesRequest = new IssuesRequest();
        issuesRequest.getIssuesFilesRequest(idTarefa);
        validatableResponse = issuesRequest.executeRequest();
    }

    @Then("e retornado que a tarefa nao existe com sucesso")
    public void retornadoQueATarefaNaoExisteComSucesso(){
        validatableResponse.statusCode(404).body(
                "message", containsString("Issue #".concat(String.valueOf(ID_TAREFA)).concat(" not found")),
                "code", is(1100),
                "localized", containsString("Issue ".concat(String.valueOf(ID_TAREFA)).concat(" not found")));
        validatableResponse.log().all();
    }

    @And("que exista um projeto com uma tarefa com arquivos cadastrados")
    public void queExistaUmProjetoComUmaTarefaComArquivosCadastrados() throws Exception {
        InsertDAO insertDAO = new InsertDAO();
        ArrayList<Integer> list = insertDAO.setInsertBugTextAndProjectAndIssueCreateNewIssueAndFilesReturnPK();
        ID_BUGTEXT = list.get(0);
        ID_PROJETO = list.get(1);
        ID_TAREFA = list.get(2);
        ID_FILEONE = list.get(4);
        ID_FILETWO = list.get(5);
    }

    @When("uma requisição de tarefa com arquivos é enviada")
    public void umaRequisicaoDeTarefaComArquivosEEnviada(){
        issuesRequest = new IssuesRequest();
        issuesRequest.getIssuesFilesRequest(String.valueOf(ID_TAREFA));
        validatableResponse = issuesRequest.executeRequest();
    }

    @When("uma requisicao de obter um arquivo de uma tarefa com o id da tarefa e id do arquivo e enviada")
    public void umaRequisicaoDeObterUmArquivoDeUmaTarefaComOIDDaTarefaEIDDoArquivoEEnviada(){
        issuesRequest = new IssuesRequest();
        issuesRequest.getIssueFileRequest(String.valueOf(ID_TAREFA),String.valueOf(ID_FILEONE));
        validatableResponse = issuesRequest.executeRequest();
    }

    @Then("uma requisição de obter um arquivo de uma tarefa é enviada com o id da tarefa que nao possue arquivo e o id do arquivo invalido")
    public void umaRequisicaoDeObterUmArquivoDeUmaTarefaEEnviadaComOIDDaTarefaQueNaoPossueArquivoEOIDDoArquivoInvalido(){
        issuesRequest = new IssuesRequest();
        issuesRequest.getIssueFileRequest(String.valueOf(ID_TAREFA), String.valueOf(1));
        validatableResponse = issuesRequest.executeRequest();
    }

    @When("^envio uma requisicao com id (.*) de tarefa e id (.*) arquivo invalido$")
    public void envioUmaRequisicaoDeISDeTarefaEIDArquivosInvalido(String idTarefa,String idArquivo){
        issuesRequest = new IssuesRequest();
        issuesRequest.getIssueFileRequest(String.valueOf(ID_TAREFA), String.valueOf(1));
        validatableResponse = issuesRequest.executeRequest();
    }

    @When("^uma requisição de obter um arquivo de uma tarefa é enviada com o id (.*) da tarefa invalido e id (.*) do arquivo invalido$")
    public void umaRequisicaoDeObterUmArquivoDeUmaTarefaEEnviadaComOIDDaTarefaInvalidoEIDDoArquivoInvalido(String idTarefa, String idArquivo){
        issuesRequest = new IssuesRequest();
        issuesRequest.getIssueFileRequest(String.valueOf(idTarefa), idArquivo);
        validatableResponse = issuesRequest.executeRequest();
    }

    @Then("o json schema de obter uma tarefa com arquivos cadastrados e validada com sucesso")
    public void jsonSchemaDeObterUmaTarefaComArquivosCadastradosEValidadoComSucesso(){
        validatableResponse.statusCode(200).body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getIssueFilesSchema.json"));
    }

    @Then("o json schema de obter um arquivo de uma tarefa e validado com sucesso")
    public void jsonSchemaDeObterUmArquivoDeUmaTarefaEValidadoComSucesso(){
        validatableResponse.statusCode(200).body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getIssueFileSingleSchema.json"));
    }

    @Then("a tarefa com arquivos e retornada com sucesso")
    public void tarefaComArquivosRetornandaComSucesso() throws JsonProcessingException {
        SelectDAO selectDAO = new SelectDAO();
        UsuarioTestBody usuarioTestBody = selectDAO.getUsuarioTestBodyMantisBTTableUsuario();
        String body = selectDAO.selectRequestJsonBody("CreateAnIssueWithAttachmentsBody");
        ObjectMapper mapper = new ObjectMapper();
        CreateAnIssueWithAttachmentsBody createAnIssueWithAttachmentsBody = mapper.readValue(body, CreateAnIssueWithAttachmentsBody.class);

        validatableResponse.statusCode(200).body(
                "files.id[0]", is(ID_FILEONE),
                "files.reporter.id[0]", is(usuarioTestBody.getId()),
                "files.reporter.name[0]", containsString(usuarioTestBody.getUsername()),
                "files.reporter.real_name[0]", containsString(usuarioTestBody.getRealname()),
                "files.reporter.email[0]", containsString(usuarioTestBody.getEmail()),
                "files.filename[0]",containsString(createAnIssueWithAttachmentsBody.getFiles()[0].getName()),
                "files.id[1]", is(ID_FILETWO),
                "files.reporter.id[1]", is(usuarioTestBody.getId()),
                "files.reporter.name[1]", containsString(usuarioTestBody.getUsername()),
                "files.reporter.real_name[1]", containsString(usuarioTestBody.getRealname()),
                "files.reporter.email[1]", containsString(usuarioTestBody.getEmail()),
                "files.filename[1]",containsString(createAnIssueWithAttachmentsBody.getFiles()[1].getName())
                );
        validatableResponse.log().all();
    }

    @Then("a tarefa com arquivo e retornada com sucesso")
    public void tarefaComArquivoERetornadaComSucesso() throws JsonProcessingException {
        SelectDAO selectDAO = new SelectDAO();
        UsuarioTestBody usuarioTestBody = selectDAO.getUsuarioTestBodyMantisBTTableUsuario();
        String body = selectDAO.selectRequestJsonBody("CreateAnIssueWithAttachmentsBody");
        ObjectMapper mapper = new ObjectMapper();
        CreateAnIssueWithAttachmentsBody createAnIssueWithAttachmentsBody = mapper.readValue(body, CreateAnIssueWithAttachmentsBody.class);
        validatableResponse.statusCode(200).body(
                "files.id[0]", is(ID_FILEONE),
                "files.reporter.id[0]", is(usuarioTestBody.getId()),
                "files.reporter.name[0]", is(usuarioTestBody.getUsername()),
                "files.reporter.real_name[0]", is(usuarioTestBody.getRealname()),
                "files.reporter.email[0]", is(usuarioTestBody.getEmail()),
                "files.filename[0]", is(createAnIssueWithAttachmentsBody.getFiles()[0].getName()));
        validatableResponse.log().all();
    }

    @And("o projeto e tarefa inseridos são deletados")
    public void oProjetoETarefaInseridosSaoDeletados(){
        DeleteDAO deleteDao = new DeleteDAO();
        //Adicionar delete bug text
        deleteDao.deleteProjectMantisBTPerIDProject(ID_PROJETO);
        deleteDao.deleteIssueMantisBTPerIDProject(ID_TAREFA);
    }

    @Then("o json schema de obter uma tarefa com sucesso e validado com sucesso")
    public void jsonSchemaDeObterUmaTarefaComSucessoEValidadoComSucesso(){
        validatableResponse.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getAIssueSchema.json"));
    }

    @When("uma requisicao de obter todas as tarefas informando a quantidade de tarefas e paginas e enviada")
    public void umaRequisicaDeObterTodasAsTarefasInformandoAQuantidadeDeTarefasEPaginasEEnviada(){
        issuesRequest = new IssuesRequest();
        issuesRequest.getAllIssuesRequest();
        validatableResponse = issuesRequest.executeRequest();
        validatableResponse.log().all();
    }

    @Then("as tarefas sao retornadas com sucesso")
    public void asTarefasSaoRetornadasComSucesso() throws JsonProcessingException {
        SelectDAO selectDAO = new SelectDAO();
        String budTextBody = selectDAO.selectRequestJsonBody("CreateBugTextTableBody");
        ObjectMapper mapperBug = new ObjectMapper();
        CreateBugTextTableBody createBugTextTableBody = mapperBug.readValue(budTextBody, CreateBugTextTableBody.class);
        String body = selectDAO.selectRequestJsonBody("CreateNewIssueBody");
        ObjectMapper mapper = new ObjectMapper();
        CreateNewIssueBody createNewIssueBody = mapper.readValue(body, CreateNewIssueBody.class);
        JsonPath jsonPath = validatableResponse.extract().jsonPath();
        List<Integer> idList =jsonPath.getList("issues.id");
        boolean resultado = false;
        for(int x = 0;x < idList.size();x++){
            if(idList.get(x).equals(ID_TAREFA)){
                validatableResponse.statusCode(200).body(
                        "issues.summary["+x+"]", containsString(createNewIssueBody.getSummary()),
                        "issues.description["+x+"]", containsString(createBugTextTableBody.getDescription()),
                        "issues.additional_information["+x+"]", containsString(createBugTextTableBody.getAdditionalInformation()),
                        "issues.project.id["+x+"]", is(Integer.parseInt(String.valueOf(ID_PROJETO))),
                        "issues.project.name["+x+"]", containsString(createNewIssueBody.getProject().getName()),
                        "issues.category.id["+x+"]", is(Integer.parseInt(String.valueOf(createNewIssueBody.getCategory().getID()))),
                        "issues.category.name["+x+"]", containsString(createNewIssueBody.getCategory().getName()),
                        "issues.view_state.id["+x+"]", is(Integer.parseInt(String.valueOf(createNewIssueBody.getViewState().getID()))),
                        "issues.view_state.name["+x+"]", containsString(createNewIssueBody.getViewState().getName()),
                        "issues.priority.name["+x+"]", containsString(createNewIssueBody.getPriority().getName()),
                        "issues.severity.name["+x+"]", containsString(createNewIssueBody.getSeverity().getName()),
                        "issues.reproducibility.name["+x+"]", containsString(createNewIssueBody.getReproducibility().getName()));
                    resultado = true;
                    break;
            }
        }
        Assert.assertTrue(resultado);

        validatableResponse.log().all();
    }

    @Then("o json schema de obter todas as tarefas e validado com sucesso")
    public void jsonSchemaDeObterTodasAsTarefasEValidadoComSucesso(){
        validatableResponse.statusCode(200).body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getAllIssuesSchema.json"));
    }

    @When("uma requisicao de obter todas as tarefas de um projeto informando o id do projeto e enviada")
    public void umaRequisicaoDeObterTodasAsTarefasDeUmProjetoInformandoOIDDoProjetoEEnviada(){
        issuesRequest = new IssuesRequest();
        issuesRequest.getIssuesForAProject(String.valueOf(ID_PROJETO));
        validatableResponse = issuesRequest.executeRequest();
        validatableResponse.log().all();
    }

    @When("^uma requisicao de obter todas as tarefas de um projeto e enviada com o id (.*) de um projeto que nao existe$")
    public void umaRequisicaoDeObterTodasAsTarefasDeUmProjetoEEnviadaComOIDDeUmProjetoQueNaoExiste(String idProjeto){
        issuesRequest = new IssuesRequest();
        issuesRequest.getIssuesForAProject(idProjeto);
        validatableResponse = issuesRequest.executeRequest();
        validatableResponse.log().all();
    }

    @When("^uma requisicao de obter todas as tarefas de um projeto com id (.*) invalido e enviado$")
    public void umaRequisicaoDeObterTodasAsTarefasDeUmProjetoComIDInvalidoEEnviado(String idProjeto){
        issuesRequest = new IssuesRequest();
        issuesRequest.getIssuesForAProject(idProjeto);
        validatableResponse = issuesRequest.executeRequest();
        validatableResponse.log().all();
    }

    @Then("e retornado que o projeto nao existe com sucesso")
    public void eRetornadoQueOProjetoNaoExisteComSucesso(){
        validatableResponse.statusCode(404);
    }

    @Then("as tarefas do projeto sao retornadas com sucesso")
    public void asTarefasDoProjetoSaoRetornadasComSucesso() throws JsonProcessingException {
        SelectDAO selectDAO = new SelectDAO();
        String budTextBody = selectDAO.selectRequestJsonBody("CreateBugTextTableBody");
        ObjectMapper mapperBug = new ObjectMapper();
        CreateBugTextTableBody createBugTextTableBody = mapperBug.readValue(budTextBody, CreateBugTextTableBody.class);
        String body = selectDAO.selectRequestJsonBody("CreateNewIssueBody");
        ObjectMapper mapper = new ObjectMapper();
        CreateNewIssueBody createNewIssueBody = mapper.readValue(body, CreateNewIssueBody.class);
        validatableResponse.statusCode(200).body(
                "issues.summary[0]", containsString(createNewIssueBody.getSummary()),
                "issues.description[0]", containsString(createBugTextTableBody.getDescription()),
                "issues.additional_information[0]", containsString(createBugTextTableBody.getAdditionalInformation()),
                "issues.project.id[0]", is(Integer.parseInt(String.valueOf(ID_PROJETO))),
                "issues.project.name[0]", containsString(createNewIssueBody.getProject().getName()),
                "issues.category.id[0]", is(Integer.parseInt(String.valueOf(createNewIssueBody.getCategory().getID()))),
                "issues.category.name[0]", containsString(createNewIssueBody.getCategory().getName()),
                "issues.view_state.id[0]", is(Integer.parseInt(String.valueOf(createNewIssueBody.getViewState().getID()))),
                "issues.view_state.name[0]", containsString(createNewIssueBody.getViewState().getName()),
                "issues.priority.name[0]", containsString(createNewIssueBody.getPriority().getName()),
                "issues.severity.name[0]", containsString(createNewIssueBody.getSeverity().getName()),
                "issues.reproducibility.name[0]", containsString(createNewIssueBody.getReproducibility().getName()));
        validatableResponse.log().all();
    }

    @Then("o json schema de obter todas as tarefas de um projeto e validado com sucesso")
    public void jsonSchemaDeObterTodasAsTarefasDeUmProjetoEValidadoComSucesso(){
        validatableResponse.statusCode(200).body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getIssuesForAProjectSchema.json"));
    }

    @When("uma requisicao obter as tarefas informando o id do filtro de tarefas e enviada")
    public void umaRequisicaoObterAsTarefasInformandoOIDDoFiltroDeTarefasEEnviada(){
        String filtro = "1";
        issuesRequest = new IssuesRequest();
        issuesRequest.getIssuesMatchingFilter(filtro);
        validatableResponse = issuesRequest.executeRequest();
        validatableResponse.log().all();
    }

    @When("^uma requisicao de obter todas as tarefas informando o id (.*) de um projeto que nao existe$")
    public void umaRequisicaoDeObterTodasAsTarefasInformandoOIDDeUmProjetoQueNaoExiste(String dado){
        issuesRequest = new IssuesRequest();
        issuesRequest.getIssuesMatchingFilter(dado);
        validatableResponse = issuesRequest.executeRequest();
        validatableResponse.log().all();
    }

    @Then("e retornado que as tarefas nao existem com sucesso")
    public void eRetornadoQueAsTarefasNaoExistemComSucesso(){
        validatableResponse.statusCode(404);
    }

    @When("^uma requisicao de obter as tarefas informando o id (.*) do filtro invalido e enviado$")
    public void umaRequisicaoDeObterAsTarefasInformandoOIDDoFiltroInvalidoEEnviado(String dado){
        issuesRequest = new IssuesRequest();
        issuesRequest.getIssuesMatchingFilter(dado);
        validatableResponse = issuesRequest.executeRequest();
        validatableResponse.log().all();
    }

    @Then("as tarefas pertencentes ao filtro sao retornadas com sucesso")
    public void asTarefasPertencentesAOFiltroSaoRetornadasComSucesso(){
        validatableResponse.statusCode(200);
        validatableResponse.log().all();
    }

    @Then("^e retornado um status code de erro (.*) com sucesso$")
    public void retornadoUmBasRequestComSucesso(int statusCode){
        validatableResponse.statusCode(statusCode);
        validatableResponse.log().all();
    }
}
