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

    @Given("que possuo um novo projeto cadastrado")
    public void quePossuoUmNovoProjetoCadastrado() throws Exception {
        InsertDAO insertDAO = new InsertDAO();
        ID_PROJETO = insertDAO.setInsertOneProjectAndReturnPk();
    }

    @When("envio uma requisicao para criar uma nova tarefa")
    public void enviarUmarequisicaoParaCriarUmaNovaTarefa() throws JsonProcessingException {
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

    @Then("o schema de criar uma nova tarefa e validado com sucesso")
    public void schemaDeCriarUmaNovaTarefaEValidadoComSucesso(){
        validatableResponse.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CreateNewIssueSchema.json"));
        JsonPath jsonPath = validatableResponse.extract().jsonPath();
        ID_TAREFA = jsonPath.get("issue.id");
    }

    @Then("o schema de criar uma nova tarefa com dados minimos e validado com sucesso")
    public void schemaDeCriarUmaNovaTarefaComDadosMinimosEValidadoComSucesso(){
        validatableResponse.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("IssueCreateAnIssueMinimalSchema.json"));
        JsonPath jsonPath = validatableResponse.extract().jsonPath();
        ID_TAREFA = jsonPath.get("issue.id");
    }

    @When("envio uma requisicao informando apenas os dados minimos para criar uma nova tarefa")
    public void envioUmaRequisicaoInformandoApenasOsDadosMinimosParaCriarUmaNovaTarefa() throws JsonProcessingException {
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

    @When("envio uma requisicao para criar uma nova tarefa de copia")
    public void envioUmaRequisicaoParaCriarUmaNovaTarefaDeCopia() throws JsonProcessingException {
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

    @Then("o schema de criar uma nova tarefa de copia e validado com sucesso")
    public void schemaDeCriarUmaNovaTarefaDeCopiaEValidadoComSucesso(){
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

    @When("envio uma requisicao para criar uma nova tarefa com arquivos")
    public void envioUmaRequisicaoParaCriarUmaNovaTarefaComArquivos() throws JsonProcessingException {
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

    @When("envio uma requisicao com o id da tarefa")
    public void envioUmaRequisicaoComOIDDaTarefa(){
        issuesRequest = new IssuesRequest();
        issuesRequest.getAnIssueRequest(String.valueOf(ID_TAREFA));
        validatableResponse = issuesRequest.executeRequest();
        validatableResponse.log().all();
    }

    @When("^que envio uma requisicao com o id (.*) de uma tarefa que nao existe$")
    public void queEnvioUmaRequisicaoComOIDDeUmaTarefaQueNaoExiste(int idTarefa){
        issuesRequest = new IssuesRequest();
        issuesRequest.getAnIssueRequest(String.valueOf(idTarefa));
        validatableResponse = issuesRequest.executeRequest();
        validatableResponse.log().all();
    }

    @Then("^a aplicacao retorna que a tarefa com o id (.*) nao foi encontrado com sucesso$")
    public void tarefaNaoERetornadaComSucesso(String idTarefa){
        validatableResponse.statusCode(404).body(
                "message", containsString("Issue #".concat(idTarefa).concat(" not found")),
                "code", is(1100),
                "localized", containsString("Issue ".concat(idTarefa).concat(" not found")));
        validatableResponse.log().all();
    }

    @When("^que informo um dado (.*) invalido na requisicao de obter uma tarefa$")
    public void queInformoUmDadoInvalidoNaREquisicaoDeObterUmaTarefa(String dado){
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

    @And("que possuo uma tarefa cadastrada")
    public void quePossuoUmaTarefaCadastrada() throws Exception {
        InsertDAO insertDAO = new InsertDAO();
        ArrayList<Integer> list = insertDAO.setInsertBugTextAndProjectAndIssueCreateNewIssueReturnPK();
        ID_BUGTEXT = list.get(0);
        ID_PROJETO = list.get(1);
        ID_TAREFA = list.get(2);
    }

    @And("que possuo um projeto cadastrado que nao contem arquivos")
    public void quePossuoUmProjetoCadastradoQueNaoContemArquivos() throws Exception {
        quePossuoUmaTarefaCadastrada();
    }

    @And("envio uma requisicao com o id de uma tarefa que nao contem arquivo")
    public void envioUmarequisicaoComOIDDeUmaTarefaQueNaoContemArquivos(){
        issuesRequest = new IssuesRequest();
        issuesRequest.getIssuesFilesRequest(String.valueOf(ID_TAREFA));
        validatableResponse = issuesRequest.executeRequest();
    }

    @When("^envio uma requisicao com id (.*) invalido de obter uma tarefa com arquivos cadastrados$")
    public void envioUmaRequisicaoComIDInvalidoDeObterUmaTarefaComarquivosCadastrados(String idTarefa){
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

    @And("que possuo um projeto com uma tarefa com arquivos cadastrados")
    public void quePossuoUmProjetoComUmaTarefaComArquivosCadastrados() throws Exception {
        InsertDAO insertDAO = new InsertDAO();
        ArrayList<Integer> list = insertDAO.setInsertBugTextAndProjectAndIssueCreateNewIssueAndFilesReturnPK();
        ID_BUGTEXT = list.get(0);
        ID_PROJETO = list.get(1);
        ID_TAREFA = list.get(2);
        ID_FILEONE = list.get(4);
        ID_FILETWO = list.get(5);
    }

    @When("envio uma requisicao de tarefa que possua arquivos")
    public void envioUmaRequisicaoDeTarefaQuePossuaArquivos(){
        issuesRequest = new IssuesRequest();
        issuesRequest.getIssuesFilesRequest(String.valueOf(ID_TAREFA));
        validatableResponse = issuesRequest.executeRequest();
    }

    @When("envio uma requisicao com o id da tarefa e id do arquivo")
    public void envioUmaRequisicaoComOIDDaTarefaEIDDoArquivo(){
        issuesRequest = new IssuesRequest();
        issuesRequest.getIssueFileRequest(String.valueOf(ID_TAREFA),String.valueOf(ID_FILEONE));
        validatableResponse = issuesRequest.executeRequest();
    }

    @Then("envio uma requisicao com o id de uma tarefa que nao possue arquivo e id de arquivo invalido")
    public void envioUmaRequisicaoComIDDeUmaTarefaEIDDearquivoInvalidoSemArquivo(){
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

    @And("deleto o projeto e tarefa inseridos")
    public void deletoOProjetoETarefaInseridos(){
        DeleteDAO deleteDao = new DeleteDAO();
        //Adicionar delete bug text
        deleteDao.deleteProjectMantisBTPerIDProject(ID_PROJETO);
        deleteDao.deleteIssueMantisBTPerIDProject(ID_TAREFA);
    }

    @Then("o json schema de obter uma tarefa com sucesso e validado com sucesso")
    public void jsonSchemaDeObterUmaTarefaComSucessoEValidadoComSucesso(){
        validatableResponse.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getAIssueSchema.json"));
    }

    @When("envio uma requisicao informando a quantidade de tarefas e paginas")
    public void envioUmaRequisicaoInformandoAQuantidadeDeTarefasEPaginas(){
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

    @When("envio uma requisicao de um projeto informando o id")
    public void envioUmaRequisicaoDeUmProjetoInformandoOID(){
        issuesRequest = new IssuesRequest();
        issuesRequest.getIssuesForAProject(String.valueOf(ID_PROJETO));
        validatableResponse = issuesRequest.executeRequest();
        validatableResponse.log().all();
    }

    @When("^envio uma requisicao com o id (.*) de um projeto que nao existe$")
    public void envioUmarequisicaoComOIDDeUmProjetoQueNaoExiste(String idProjeto){
        issuesRequest = new IssuesRequest();
        issuesRequest.getIssuesForAProject(idProjeto);
        validatableResponse = issuesRequest.executeRequest();
        validatableResponse.log().all();
    }

    @When("^envio uma requisicao de obter todas as tarefas de um projeto com id (.*) invalido$")
    public void envioUmarequisicaoDeObterTodasAsTarefasDeUmProjetoComIDInvalido(String idProjeto){
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

    @When("envio uma requisicao informando o id do filtro de tarefas")
    public void envioUmaRequisicaoInformandoOIDDoFiltroDeTarefas(){
        String filtro = "1";
        issuesRequest = new IssuesRequest();
        issuesRequest.getIssuesMatchingFilter(filtro);
        validatableResponse = issuesRequest.executeRequest();
        validatableResponse.log().all();
    }

    @When("^envio uma requisicao informando um dado (.*) invalido do filtro$")
    public void envioUmaRequisicaoInformandoUmDadoInvalidoDoFiltro(String dado){
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
