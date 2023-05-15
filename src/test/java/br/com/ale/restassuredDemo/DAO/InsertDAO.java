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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class InsertDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsertDAO.class);
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

}
