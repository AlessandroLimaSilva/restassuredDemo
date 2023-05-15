package br.com.ale.restassuredDemo.DAO;

import br.com.ale.restassuredDemo.Body.CreateAnIssueWithAttachmentsRequestBody.CreateAnIssueWithAttachmentsBody;
import br.com.ale.restassuredDemo.Body.CreateAnIssueWithAttachmentsRequestBody.Field;
import br.com.ale.restassuredDemo.Body.CreateNewIssueRequestBody.CreateNewIssueBody;
import br.com.ale.restassuredDemo.Body.IssueCreateAnIssueMinimalRequestBody.CreateBugTextTableBody;
import br.com.ale.restassuredDemo.Body.UtilsRequestBody.BugHistoryTableBody;
import br.com.ale.restassuredDemo.Body.UtilsRequestBody.ProjetoBody;
import br.com.ale.restassuredDemo.Types.UsuariosType;
import br.com.ale.restassuredDemo.utils.UtilsQuery;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InsertDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsertDAO.class);

    private String inserirTodosUsuariosBDMantis = "src/test/resources/query/InsertTodosUsuariosBDMantis.sql";
    private String allUsers = "src/test/resources/query/InsertTodosUsuarios.sql";
    private String allProjects = "src/test/resources/query/InsertTodosProjetos.sql";
    private String administrador = "src/test/resources/query/UserAdministrador.sql";
    private String atualizador = "src/test/resources/query/UserAtualizador.sql";
    private String desenvolvedor = "src/test/resources/query/UserDesenvolvedor.sql";
    private String gerente = "src/test/resources/query/UserGerente.sql";
    private String relator = "src/test/resources/query/UserRelator.sql";
    private String visualizador = "src/test/resources/query/UserVisualizador.sql";
    public static String INSERIR_UM_PROJETO_MANTISBT_PROJECT_TABLE_FILE = "src/test/resources/query/InsertUmProjetoNoMantiBTProjectTable.sql";

    public InsertDAO(){}

    public void insertlistaDeInstrucoesSQL(String arquivoSQL){
        try{
            ArrayList<String> lista = UtilsQuery.lerSQL(new File(arquivoSQL));
            for(String sql: lista){
                setDataInsert(sql);
            }
        }catch (Exception ex) {
            System.out.println("Falha ocorrida setInsertUsuarioTableMantisBTDataBase : "+ex.getMessage());
        }

    }

    public void setDataInsert(String sql) throws Exception {
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            PreparedStatement pst = connectionFactory.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.executeUpdate();
            connectionFactory.transactionConfirm();
            pst.close();
            connectionFactory.closeConnection();
        } catch (SQLException ex) {
            throw new SQLException("Falha ocorrida setDataInsert: "+ex.getMessage());
        }
    }

    public void setDataInsert(String sql,String enderecoBanco,String usuario,String senha) throws Exception {
        try{
            LOGGER.info(sql);
            ConnectionFactory connectionFactory = new ConnectionFactory(enderecoBanco,usuario,senha);
            PreparedStatement pst = connectionFactory.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.executeUpdate();
            connectionFactory.transactionConfirm();
            LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
            pst.close();
            connectionFactory.closeConnection();
        } catch (SQLException ex) {
            throw new SQLException("Falha ocorrida setDataInsert: "+sql+"\n"+ex.getMessage());
        }
    }

    public void setInsertOneIssue() throws Exception {
        insertlistaDeInstrucoesSQL(INSERIR_UM_PROJETO_MANTISBT_PROJECT_TABLE_FILE);
        int idBugText = setInsertOneBugTextTable();
        SelectDAO selectDAO = new SelectDAO();
        ArrayList<String> list = selectDAO.returnNameIDInSearchProjectIDPerNameInMantisProjectTable("teste01");
        int idProjeto = Integer.parseInt(list.get(0));
        String body = selectDAO.selectRequestJsonBody("CreateNewIssueBody");
        ObjectMapper mapper = new ObjectMapper();
        CreateNewIssueBody createNewIssueBody = mapper.readValue(body, CreateNewIssueBody.class);
        int data = UtilsQuery.getDataEpochTime();
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            String sql = "INSERT INTO bugtracker.mantis_bug_table (project_id,reporter_id,reproducibility,bug_text_id,summary,date_submitted,last_updated) " +
                    "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pst = connectionFactory.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, idProjeto);//Project id
            pst.setInt(2, 1);//Administrator id =1
            pst.setInt(3, 10);//Reproducity 10 = always
            pst.setInt(4, idBugText);//Bug text id
            pst.setString(5, "criadoParaTeste");
            pst.setString(6, String.valueOf(data));
            pst.setString(7, String.valueOf(data));
            pst.executeUpdate();
            connectionFactory.transactionConfirm();
            LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
            pst.close();
            connectionFactory.closeConnection();
        } catch (SQLException ex) {
            throw new SQLException("Falha ocorrida setDataInsertOneUser: "+ex.getMessage());
        }
    }

    public int setInsertFilesAndReturnPk(String fileName, String file,int bugID) throws Exception {
        int id = 0;
        SelectDAO selectDAO = new SelectDAO();
        selectDAO.selectAndDeleteIfExistMantidBTFile(fileName);

        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            String sql = "INSERT INTO bugtracker.mantis_bug_file_table(bug_id, filename, filesize, file_type, content, date_added, user_id) " +
                    "VALUES(?,?,?,?,?,?,?);";
            PreparedStatement pst = connectionFactory.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, bugID);//bugId
            pst.setString(2, fileName);//Filename
            pst.setInt(3, 40647);//FileSize
            pst.setString(4, "image/png; charset=binary");//FileType
            pst.setString(5, file);//Content
            pst.setInt(6, UtilsQuery.getDataEpochTime());//dateAdded
            pst.setInt(7, 1);//userId
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            connectionFactory.transactionConfirm();
            LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
            pst.close();
            connectionFactory.closeConnection();
        } catch (SQLException ex) {
            throw new SQLException("setInsertOneProjectAndReturnPk : "+ex.getMessage());
        }
        return id;
    }

    public int setInsertOneProjectAndReturnPk() throws Exception {
        int id = 0;
        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("ProjetoBody");
        ObjectMapper mapper = new ObjectMapper();
        ProjetoBody projetoBody = mapper.readValue(body, ProjetoBody.class);
        selectDAO.selectAndDeleteIfExistMantidBTProject(projetoBody.getName());

        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            String sql = "INSERT INTO bugtracker.mantis_project_table(NAME, STATUS, ENABLED, VIEW_STATE, ACCESS_MIN, DESCRIPTION, CATEGORY_ID, INHERIT_GLOBAL) " +
                    "VALUES(?,?,?,?,?,?,?,?);";
            PreparedStatement pst = connectionFactory.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, projetoBody.getName());
            pst.setInt(2, projetoBody.getStatus());
            pst.setInt(3, projetoBody.getEnabled());
            pst.setInt(4, projetoBody.getViewState());//Bug text id
            pst.setInt(5, projetoBody.getAccessMin());
            pst.setString(6, projetoBody.getDescription());
            pst.setInt(7, projetoBody.getCategoryID());
            pst.setInt(8, projetoBody.getInheritGlobal());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            connectionFactory.transactionConfirm();
            LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
            pst.close();
            connectionFactory.closeConnection();
        } catch (SQLException ex) {
            throw new SQLException("setInsertOneProjectAndReturnPk : "+ex.getMessage());
        }
        return id;
    }

    public ArrayList<Integer> setInsertBugTextAndProjectAndIssueCreateNewIssueReturnPK() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(setInsertOneBugTextTable());
        list.add(setInsertOneProjectAndReturnPk());
        list.add(setInsertOneCreateNewIssueAndReturnPk(list.get(0),list.get(1)));
        list.add(setInsertOneBugHistoryTable(list.get(2)));
        return list;
    }

    public ArrayList<Integer> setInsertBugTextAndProjectAndIssueCreateNewIssueAndFilesReturnPK() throws Exception {
        ArrayList<Integer> list = setInsertBugTextAndProjectAndIssueCreateNewIssueReturnPK();
        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("CreateAnIssueWithAttachmentsBody");
        ObjectMapper mapper = new ObjectMapper();
        CreateAnIssueWithAttachmentsBody createAnIssueWithAttachmentsBody = mapper.readValue(body, CreateAnIssueWithAttachmentsBody.class);

        list.add(setInsertFilesAndReturnPk(
                createAnIssueWithAttachmentsBody.getFiles()[0].getName(),
                createAnIssueWithAttachmentsBody.getFiles()[0].getContent(),
                list.get(2))
        );

        list.add(setInsertFilesAndReturnPk(
                createAnIssueWithAttachmentsBody.getFiles()[1].getName(),
                createAnIssueWithAttachmentsBody.getFiles()[1].getContent(),
                list.get(2)));

        return list;
    }

    public int setInsertOneCreateNewIssueAndReturnPk(int bugTextID, int projectID) throws Exception {
        int resposta = 0;
        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("CreateNewIssueBody");
        ObjectMapper mapper = new ObjectMapper();
        CreateNewIssueBody createNewIssueBody = mapper.readValue(body, CreateNewIssueBody.class);

        int data = UtilsQuery.getDataEpochTime();
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            String sql = "INSERT INTO bugtracker.mantis_bug_table (project_id,reporter_id,reproducibility,bug_text_id,summary,date_submitted,last_updated) " +
                    "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pst = connectionFactory.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, projectID);//Project id
            pst.setInt(2, 1);//Administrator id =1
            pst.setInt(3, 10);//Reproducity 10 = always
            pst.setInt(4, bugTextID);//Bug text id
            pst.setString(5, createNewIssueBody.getSummary());//summary
            pst.setString(6, String.valueOf(data));//Data
            pst.setString(7, String.valueOf(data));//Data
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                resposta = rs.getInt(1);
            }
            connectionFactory.transactionConfirm();
            LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
            pst.close();
            connectionFactory.closeConnection();
        } catch (SQLException ex) {
            throw new SQLException("Falha ocorrida setDataInsertOneUser: "+ex.getMessage());
        }
        return resposta;
    }

    public int setInsertOneBugTextTable() throws JsonProcessingException {
        int id = 0;
        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("CreateBugTextTableBody");
        ObjectMapper mapper = new ObjectMapper();
        CreateBugTextTableBody createBugTextTableBody = mapper.readValue(body, CreateBugTextTableBody.class);
        selectDAO.selectAndDeleteIfExistBugText(createBugTextTableBody.getStepsToReproduce());
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            String sql = "INSERT INTO bugtracker.mantis_bug_text_table (description,steps_to_reproduce,additional_information) VALUES (?,?,?);";
            PreparedStatement pst = connectionFactory.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, createBugTextTableBody.getDescription());
            pst.setString(2, createBugTextTableBody.getStepsToReproduce());
            pst.setString(3, createBugTextTableBody.getAdditionalInformation());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            connectionFactory.transactionConfirm();
            pst.close();
            connectionFactory.closeConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public int setInsertOneBugHistoryTable(int bugID) throws JsonProcessingException {
        int id = 0;
        SelectDAO selectDAO = new SelectDAO();
        String body = selectDAO.selectRequestJsonBody("BugHistoryTableBody");
        ObjectMapper mapper = new ObjectMapper();
        BugHistoryTableBody bugHistoryTableBody = mapper.readValue(body, BugHistoryTableBody.class);
        bugHistoryTableBody.setBugID(bugID);
        //selectDAO.selectAndDeleteIfExistBugText(createBugTextTableBody.getStepsToReproduce());
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            String sql = "INSERT INTO bugtracker.mantis_bug_history_table (user_id,bug_id,field_name,old_value,new_value,type,date_modified) VALUES (?,?,?,?,?,?,?);";
            PreparedStatement pst = connectionFactory.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, bugHistoryTableBody.getUserID());//userID = 1
            pst.setInt(2, bugHistoryTableBody.getBugID());//BugID =
            pst.setString(3, bugHistoryTableBody.getFieldName());//FieldName = ""
            pst.setString(4, bugHistoryTableBody.getOldValue());//OldValue "mantishub"
            pst.setString(5, bugHistoryTableBody.getNewValue());//newValue ""
            pst.setInt(6, bugHistoryTableBody.getType());//Type 25
            pst.setInt(7, bugHistoryTableBody.getData());//DataModified
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            connectionFactory.transactionConfirm();
            pst.close();
            connectionFactory.closeConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public Field setInsertOneCustomFieldTable(){
        int id = 0;
        String nome = "CustomFieldTest";
        String possibleValues = "Seattle";
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            String sql = "INSERT INTO bugtracker.mantis_custom_field_table (name,possible_values) VALUES (?,?);";
            PreparedStatement pst = connectionFactory.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, nome);
            pst.setString(2, possibleValues);
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            connectionFactory.transactionConfirm();
            pst.close();
            connectionFactory.closeConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Field field = new Field();
        field.setName(nome);
        field.setID(id);
        return field;
    }

    public void setInsertOneUser(Map<String,Object> map) throws Exception {
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            String oneUser = "INSERT INTO mantis_user_table(username,realname,email,enabled,protected,access_level,cookie_string) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pst = connectionFactory.getConnection().prepareStatement(oneUser, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, map.get("username").toString());
            pst.setString(2,map.get("realname").toString());
            pst.setString(3, map.get("email").toString());
            pst.setString(4, map.get("enabled").toString());
            pst.setString(5, map.get("protected").toString());
            pst.setString(6, map.get("acesseLevel").toString());
            pst.setString(7, map.get("cookieString").toString());
            pst.executeUpdate();
            connectionFactory.transactionConfirm();
            LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
            pst.close();
            connectionFactory.closeConnection();
        } catch (SQLException ex) {
            throw new SQLException("Falha ocorrida setDataInsertOneUser: "+ex.getMessage());
        }
    }

    public int setInsertUsuarioTableMantisBTDataBaseAndReturnID(UsuariosType usuariosType) throws SQLException {
        int id = 0;
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            String oneUser = "INSERT INTO bugtracker.mantis_user_table(username,realname,email,enabled,protected,access_level,cookie_string) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pst = connectionFactory.getConnection().prepareStatement(oneUser, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, usuariosType.getUserName());
            pst.setString(2, usuariosType.getRealName());
            pst.setString(3, usuariosType.getEmail());
            pst.setString(4, usuariosType.getEnabled());
            pst.setString(5, usuariosType.getProtekted());
            pst.setString(6, usuariosType.getAccessLevel());
            pst.setString(7, usuariosType.getCookieString());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            connectionFactory.transactionConfirm();
            pst.close();
            connectionFactory.closeConnection();
        } catch (SQLException ex) {
            throw new SQLException("Falha ocorrida setInsertUsuarioTableMantisBTDataBaseAndReturnID : "+ex.getMessage());
        }
        return id;
    }

    public void setInsertUsuarioTableMantisBTDataBase(UsuariosType usuariosType){
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            String oneUser = "INSERT INTO bugtracker.mantis_user_table(username,realname,email,enabled,protected,access_level,cookie_string) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pst = connectionFactory.getConnection().prepareStatement(oneUser, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, usuariosType.getUserName());
            pst.setString(2, usuariosType.getRealName());
            pst.setString(3, usuariosType.getEmail());
            pst.setString(4, usuariosType.getEnabled());
            pst.setString(5, usuariosType.getProtekted());
            pst.setString(6, usuariosType.getAccessLevel());
            pst.setString(7, usuariosType.getCookieString());
            pst.executeUpdate();
            connectionFactory.transactionConfirm();
            LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
            pst.close();
            connectionFactory.closeConnection();
        } catch (Exception ex) {
            System.out.println("Falha ocorrida setInsertUsuarioTableMantisBTDataBase : "+ex.getMessage());
        }
    }

    public void setDataInsertOneUser(String nomeUsuario) throws Exception {
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            String oneUser = "INSERT INTO mantis_user_table(username,realname,email,enabled,protected,access_level,cookie_string) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pst = connectionFactory.getConnection().prepareStatement(oneUser, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, nomeUsuario);
            pst.setString(2,"teste");
            pst.setString(3, nomeUsuario+"@teste.com");
            pst.setString(4, "1");
            pst.setString(5, "0");
            pst.setString(6, "90");
            pst.setString(7,nomeUsuario);
            pst.executeUpdate();
            connectionFactory.transactionConfirm();
            LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
            pst.close();
            connectionFactory.closeConnection();
        } catch (SQLException ex) {
            throw new SQLException("Falha ocorrida setDataInsertOneUser: "+ex.getMessage());
        }
    }

    public void setInsertUsuariosTableUsuario(ArrayList<UsuariosType> lista){
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            String oneUser = "INSERT INTO mantis_user_table(username,realname,email,enabled,protected,access_level,cookie_string,password,) VALUES(?,?,?,?,?,?,?,?)";
            for(UsuariosType type : lista){
                PreparedStatement pst = connectionFactory.getConnection().prepareStatement(oneUser, Statement.RETURN_GENERATED_KEYS);
                pst.setString(1, type.getUserName());
                pst.setString(2, type.getRealName());
                pst.setString(3, type.getEmail());
                pst.setString(4, type.getEnabled());
                pst.setString(5, type.getProtekted());
                pst.setString(6, type.getAccessLevel());
                pst.setString(7, type.getCookieString());
                pst.executeUpdate();
                connectionFactory.transactionConfirm();
                LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
                pst.close();
            }
            connectionFactory.closeConnection();
        } catch (Exception ex) {
            System.out.println("Falha ocorrida setInsertUsuarioTableUsuario: \n"+ex.getMessage());
        }
    }

    public void setInsertOneProjeto(Map<String,Object> map) throws Exception {
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            String sql =
                    "INSERT INTO mantis_project_table (name,status,enabled,view_state,access_min,description,category_id,inherit_global)\n" +
                    "VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pst = connectionFactory.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            LOGGER.info("MAP = "+map.get("name").toString());
            pst.setString(1, map.get("name").toString());
            pst.setInt(2, (Integer) map.get("status"));
            pst.setInt(3, (Integer) map.get("enabled"));
            pst.setInt(4, (Integer) map.get("viewState"));
            pst.setInt(5, (Integer) map.get("accessMin"));
            pst.setString(6, map.get("description").toString());
            pst.setInt(7, (Integer) map.get("categoryID"));
            pst.setInt(8, (Integer) map.get("inheritGlobal"));
            pst.executeUpdate();
            connectionFactory.transactionConfirm();
            LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
            pst.close();
            connectionFactory.closeConnection();
        } catch (SQLException ex) {
            throw new SQLException("Falha ocorrida setInsertOneProjeto: "+ex.getMessage());
        }
    }

    public void insertNovaCategoriaGlobalPorNome(String name) throws Exception {
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            String sql = "INSERT INTO mantis_category_table (name) VALUES (?)";
            PreparedStatement pst = connectionFactory.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            LOGGER.info("MAP = "+sql+" name = "+name);
            pst.setString(1, name);
            pst.executeUpdate();
            connectionFactory.transactionConfirm();
            LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
            pst.close();
            connectionFactory.closeConnection();
        } catch (SQLException ex) {
            throw new SQLException("Falha ocorrida insertNovaCategoriaGlobalPorNome: "+ex.getMessage());
        }
    }

    public void insertNovoSubprojeto(String idProjeto,String idSubprojeto) throws Exception {
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            String sql = "INSERT INTO mantis_project_hierarchy_table(child_id, parent_id, inherit_parent) VALUES (?,?,?)";
            PreparedStatement pst = connectionFactory.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, idSubprojeto);
            pst.setString(2, idProjeto);
            pst.setString(3, "1");
            pst.executeUpdate();
            connectionFactory.transactionConfirm();
            pst.close();
            connectionFactory.closeConnection();
        } catch (SQLException ex) {
            throw new SQLException("Falha ocorrida insertNovoSubprojeto: "+ex.getMessage());
        }
    }


    public int dataInsertReturnPK(String insert, String dado) throws Exception {
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            PreparedStatement pst =
                    connectionFactory.getConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, dado);
            pst.executeUpdate();
            connectionFactory.transactionConfirm();
            ResultSet rs = pst.getGeneratedKeys();
            rs.next();
            int pk = rs.getInt(1);
            rs.close();
            pst.close();
            connectionFactory.closeConnection();
            return pk;

        } catch (SQLException ex) {
            throw new SQLException("Falha ocorrida dataInsertReturnPK : "+ex.getMessage());
        }
    }

    public String getAllUsers(){
        try{
            allUsers = UtilsQuery.lerConteudoSQL(new File(allUsers));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    public void  setInserirTodosUsuariosBDMantis(){
        try{
            String sql = UtilsQuery.lerConteudoSQL(new File(inserirTodosUsuariosBDMantis));
            ConnectionFactory connectionFactory = new ConnectionFactory();
            PreparedStatement pst = connectionFactory.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.executeUpdate();
            connectionFactory.transactionConfirm();
            pst.close();
            connectionFactory.closeConnection();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String getAllProjects(){
        try{
            allProjects = UtilsQuery.lerConteudoSQL(new File(allProjects));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return allProjects;
    }

    public String getAdministrador(){
        try{
            administrador = UtilsQuery.lerConteudoSQL(new File(administrador));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return administrador;
    }

    public String getAtualizador(){
        try{
            atualizador = UtilsQuery.lerConteudoSQL(new File(atualizador));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return atualizador;
    }

    public String getDesenvolvedor(){
        try{
            desenvolvedor = UtilsQuery.lerConteudoSQL(new File(desenvolvedor));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return desenvolvedor;
    }

    public String getGerente(){
        try{
            gerente = UtilsQuery.lerConteudoSQL(new File(gerente));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gerente;
    }

    public String getRelator(){
        try{
            relator = UtilsQuery.lerConteudoSQL(new File(relator));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return relator;
    }

    public String getVisualizador(){
        try{
            visualizador = UtilsQuery.lerConteudoSQL(new File(visualizador));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return visualizador;
    }

    public ArrayList<String> getT(String endereco) throws FileNotFoundException {
        return UtilsQuery.lerSQL(new File(endereco));
    }

    public String indexInsertSql(String nome){
        Map<String, String> indexMap = new HashMap<String,String>();
        indexMap.put("administrador",getAdministrador());
        indexMap.put("gerente",getGerente());
        indexMap.put("desenvolvedor",getDesenvolvedor());
        indexMap.put("atualizador",getAtualizador());
        indexMap.put("relator",getRelator());
        indexMap.put("visualizador",getVisualizador());

        return indexMap.get(nome);
    }

}
