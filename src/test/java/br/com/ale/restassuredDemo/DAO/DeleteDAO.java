package br.com.ale.restassuredDemo.DAO;

import br.com.ale.restassuredDemo.utils.UtilsQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class DeleteDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteDAO.class);

    private String deletarTodosUsuarios = "DELETE FROM mantis_user_table where id >= 2;";
    private String deletarTodosProjetos = "DELETE FROM mantis_project_table where id >= 1 AND id <=40;";

    public static String DELETEALLPROJECTSMANTISBT = "src/test/resources/query/DeleteAllProjectsMantisBT.sql";

    public static ArrayList<Integer> searchNameIds(ArrayList<String> names) {
        ArrayList<Integer> ids = new ArrayList<Integer>();

        try {

            ConnectionFactoryDAO conn = new ConnectionFactoryDAO();
            String sql = "SELECT id FROM mantis_project_table WHERE name = ?";
            PreparedStatement pstmt = conn.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for (String name : names) {
                pstmt.setString(1, name);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    ids.add(rs.getInt("id"));
                } else {
                    System.out.println("No results found for name: " + name);
                }
                rs.close();
            }

            pstmt.close();
            conn.closeConnection();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return ids;
    }

    public void deletarUsuarioPorNomeMantisBTTableUsuario(String nome){
        try{
            ConnectionFactoryDAO connectionFactoryDAO = new ConnectionFactoryDAO();
            String oneUser = "DELETE FROM bugtracker.mantis_user_table WHERE USERNAME = ?";
            PreparedStatement pst = connectionFactoryDAO.getConnection().prepareStatement(oneUser, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, nome);
            pst.executeUpdate();
            connectionFactoryDAO.transactionConfirm();
            LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
            pst.close();
            connectionFactoryDAO.closeConnection();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void deletarUsuarioPorEmailMantisBTTableUsuario(String email){
        try{
            ConnectionFactoryDAO connectionFactoryDAO = new ConnectionFactoryDAO();
            String oneUser = "DELETE FROM bugtracker.mantis_user_table WHERE email = ?";
            PreparedStatement pst = connectionFactoryDAO.getConnection().prepareStatement(oneUser, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, email);
            pst.executeUpdate();
            connectionFactoryDAO.transactionConfirm();
            LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
            pst.close();
            connectionFactoryDAO.closeConnection();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void deletarUsuarioPorNomeRealMantisBTTableUsuario(String nome){
        try{
            ConnectionFactoryDAO connectionFactoryDAO = new ConnectionFactoryDAO();
            String oneUser = "DELETE FROM bugtracker.mantis_user_table WHERE REALNAME = ?";
            PreparedStatement pst = connectionFactoryDAO.getConnection().prepareStatement(oneUser, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, nome);
            pst.executeUpdate();
            connectionFactoryDAO.transactionConfirm();
            LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
            pst.close();
            connectionFactoryDAO.closeConnection();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void deleteProjectMantisBTPerIDProject(int id){
        try{
            ConnectionFactoryDAO connectionFactoryDAO = new ConnectionFactoryDAO();
            String oneUser = "DELETE FROM bugtracker.mantis_project_table WHERE id = ?;";
            PreparedStatement pst = connectionFactoryDAO.getConnection().prepareStatement(oneUser, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, id);
            pst.executeUpdate();
            connectionFactoryDAO.transactionConfirm();
            LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
            pst.close();
            connectionFactoryDAO.closeConnection();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void deleteFileMantisBTPerID(int id){
        try{
            ConnectionFactoryDAO connectionFactoryDAO = new ConnectionFactoryDAO();
            String oneUser = "DELETE FROM bugtracker.mantis_bug_file_table WHERE id = ?;";
            PreparedStatement pst = connectionFactoryDAO.getConnection().prepareStatement(oneUser, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, id);
            pst.executeUpdate();
            connectionFactoryDAO.transactionConfirm();
            LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
            pst.close();
            connectionFactoryDAO.closeConnection();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void deleteIssueMantisBTPerIDProject(int id){
        try{
            ConnectionFactoryDAO connectionFactoryDAO = new ConnectionFactoryDAO();
            String oneUser = "DELETE FROM bugtracker.mantis_bug_table WHERE id = ?;";
            PreparedStatement pst = connectionFactoryDAO.getConnection().prepareStatement(oneUser, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, id);
            pst.executeUpdate();
            connectionFactoryDAO.transactionConfirm();
            LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
            pst.close();
            connectionFactoryDAO.closeConnection();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void deleteBUGTextMantisBTPerIDProject(int id){
        try{
            ConnectionFactoryDAO connectionFactoryDAO = new ConnectionFactoryDAO();
            String oneUser = "DELETE FROM bugtracker.mantis_bug_text_table WHERE id = ?;";
            PreparedStatement pst = connectionFactoryDAO.getConnection().prepareStatement(oneUser, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, id);
            pst.executeUpdate();
            connectionFactoryDAO.transactionConfirm();
            LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
            pst.close();
            connectionFactoryDAO.closeConnection();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void deleteDataByIds(ArrayList<Object> ids) {
        try {
            ConnectionFactoryDAO conn = new ConnectionFactoryDAO();
            String sql = "DELETE FROM bugtracker.mantis_project_table WHERE id = ?";
            PreparedStatement pstmt = conn.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (Object id : ids) {
                pstmt.setInt(1, Integer.parseInt(String.valueOf(id)));
                int result = pstmt.executeUpdate();
                if (result == 0) {
                    System.out.println("No rows deleted for ID: " + id);
                } else {
                    System.out.println("Deleted row with ID: " + id);
                }
            }
            pstmt.close();
            conn.closeConnection();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



    public void deleteTodosUsuariosMantisBD(){
        try{
            ArrayList<String> lista = UtilsQuery.lerSQL(new File(DELETEALLPROJECTSMANTISBT));
            for(Object type : lista){
                ConnectionFactoryDAO connectionFactoryDAO = new ConnectionFactoryDAO();
                String oneUser = "DELETE FROM bugtracker.mantis_project_table WHERE name = '"+type+"'";
                PreparedStatement pst = connectionFactoryDAO.getConnection().prepareStatement(oneUser, Statement.RETURN_GENERATED_KEYS);
                //pst.setString(1, String.valueOf(type));
                pst.executeUpdate();
                connectionFactoryDAO.transactionConfirm();
                LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
                pst.close();
                connectionFactoryDAO.closeConnection();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void deletelistaDeInstrucoesSQL(String arquivoSQL){
        try{
            SelectDAO selectDAO = new SelectDAO();
            ArrayList<String> lista = UtilsQuery.lerSQL(new File(arquivoSQL));
            for(String element: lista){
                System.out.println(lista);
            }
            //ArrayList<Integer> idList = new ArrayList<Integer>();
            ArrayList<Integer> idList = searchNameIds(lista);
            for(String nome: lista){
                deleteProjectPerName(nome);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void setDataDelete(String sql){
        try{
            ConnectionFactoryDAO connectionFactoryDAO = new ConnectionFactoryDAO();
            PreparedStatement pst = connectionFactoryDAO.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.executeUpdate();
            connectionFactoryDAO.transactionConfirm();
            pst.close();
            connectionFactoryDAO.closeConnection();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void setDeletarTodosUsuariosMantisBD(){
        try{
            ConnectionFactoryDAO connectionFactoryDAO = new ConnectionFactoryDAO();
            PreparedStatement pst = connectionFactoryDAO.getConnection().prepareStatement(deletarTodosUsuarios, Statement.RETURN_GENERATED_KEYS);
            pst.executeUpdate();
            connectionFactoryDAO.transactionConfirm();
            pst.close();
            connectionFactoryDAO.closeConnection();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public String getDeletarTodosUsuarios(){
        return deletarTodosUsuarios;
    }

    public String getDeletarTodosProjetos(){
        return deletarTodosProjetos;
    }

    public void deleteCategoryPerName(String nameProject){
        LOGGER.info(ConnectionFactoryDAO.CONNECTION_BUG_TRACKER);
        try{
            ConnectionFactoryDAO connectionFactoryDAO = new ConnectionFactoryDAO(ConnectionFactoryDAO.CONNECTION_BUG_TRACKER);
            String teste = "DELETE FROM mantis_category_table WHERE name = '"+nameProject+"';";
            LOGGER.info(teste);
            PreparedStatement pst = connectionFactoryDAO.getConnection().prepareStatement(teste, Statement.RETURN_GENERATED_KEYS);
            pst.executeUpdate();
            connectionFactoryDAO.transactionConfirm();
            pst.close();
            connectionFactoryDAO.closeConnection();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void deleteProjectPerName(String nameToDelete){
        LOGGER.info(ConnectionFactoryDAO.CONNECTION_BUG_TRACKER);
        try{
            String sqlCheck = "SELECT COUNT(*) FROM bugtracker.mantis_project_table WHERE name = ?";
            ConnectionFactoryDAO connectionFactoryDAO = new ConnectionFactoryDAO(ConnectionFactoryDAO.CONNECTION_BUG_TRACKER);
            PreparedStatement pstmtCheck = connectionFactoryDAO.getConnection().prepareStatement(sqlCheck, Statement.RETURN_GENERATED_KEYS);
            pstmtCheck.setString(1, nameToDelete);
            ResultSet rs = pstmtCheck.executeQuery();
            rs.next();
            int rowCount = rs.getInt(1);
            if (rowCount == 0) {
                System.out.println(nameToDelete + " not found.");
                return;
            }
            String sqlDelete = "DELETE FROM mantis_project_table WHERE name = ?";
            PreparedStatement pstmtDelete = connectionFactoryDAO.getConnection().prepareStatement(sqlDelete, Statement.RETURN_GENERATED_KEYS);;
            pstmtDelete.setString(1, nameToDelete);
            int rowsDeleted = pstmtDelete.executeUpdate();
            System.out.println(rowsDeleted + " rows deleted.");
            rs.close();
            pstmtCheck.close();
            pstmtDelete.close();
            connectionFactoryDAO.closeConnection();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void deleteSubProjectPerName(String idSubPorject,String idProject){
        try{
            ConnectionFactoryDAO connectionFactoryDAO = new ConnectionFactoryDAO(ConnectionFactoryDAO.CONNECTION_BUG_TRACKER);
            String teste = "DELETE from mantis_project_hierarchy_table where child_id = '"+idSubPorject+"' AND parent_id = '"+idProject+"';";
            LOGGER.info(teste);
            PreparedStatement pst = connectionFactoryDAO.getConnection().prepareStatement(teste, Statement.RETURN_GENERATED_KEYS);
            pst.executeUpdate();
            connectionFactoryDAO.transactionConfirm();
            pst.close();
            connectionFactoryDAO.closeConnection();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void deleteUserPerName(String nameUser){
        LOGGER.info(ConnectionFactoryDAO.CONNECTION_BUG_TRACKER);
        try{
            ConnectionFactoryDAO connectionFactoryDAO = new ConnectionFactoryDAO(ConnectionFactoryDAO.CONNECTION_BUG_TRACKER);
            String teste = "DELETE FROM mantis_user_table WHERE username = '"+nameUser+"';";
            LOGGER.info(teste);
            PreparedStatement pst = connectionFactoryDAO.getConnection().prepareStatement(teste, Statement.RETURN_GENERATED_KEYS);
            pst.executeUpdate();
            connectionFactoryDAO.transactionConfirm();
            pst.close();
            connectionFactoryDAO.closeConnection();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void deletarBugTextPorDescriptionMantisBT(String nome){
        try{
            ConnectionFactoryDAO connectionFactoryDAO = new ConnectionFactoryDAO();
            String oneUser = "DELETE FROM bugtracker.mantis_bug_text_table WHERE description = ?;";
            PreparedStatement pst = connectionFactoryDAO.getConnection().prepareStatement(oneUser, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, nome);
            pst.executeUpdate();
            connectionFactoryDAO.transactionConfirm();
            LOGGER.info(String.valueOf(pst.getGeneratedKeys()));
            pst.close();
            connectionFactoryDAO.closeConnection();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
