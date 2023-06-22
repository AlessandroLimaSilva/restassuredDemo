package br.com.ale.restassuredDemo.DAO;

import br.com.ale.restassuredDemo.Body.UtilsRequestBody.UsuarioTestBody;
import br.com.ale.restassuredDemo.Types.UsuariosType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(SelectDAO.class);
    private static final String beneficiariosAtivos = "src/test/resources/sql/Beneficiarios_Ativos.sql";

    private static final String usuarioSelectFromName = "";
    private static final String projetoSelectFromNome = "";

    public ArrayList<String> getQueryResult(String query) throws Exception {
        ArrayList<String> arrayList = null;
        ConnectionFactoryDAO connection = new ConnectionFactoryDAO();
        Statement stmt = null;

        try {

            stmt = connection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if(!rs.isBeforeFirst()){
                return null;
            }

            else{
                int numberOfColumns;
                ResultSetMetaData metadata=null;

                arrayList = new ArrayList<String>();
                metadata = rs.getMetaData();
                numberOfColumns = metadata.getColumnCount();

                while (rs.next()) {
                    int i = 1;
                    while(i <= numberOfColumns) {
                        arrayList.add(rs.getString(i++));
                    }
                }
            }

            rs.close();
            stmt.close();
            connection.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                connection.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public String searchProjectIDPerNameInMantisProjectTable(String nome){
        String query = "SELECT id, name FROM bugtracker.mantis_project_table WHERE name = '"+nome+"';";
        String resposta = null;
        ConnectionFactoryDAO connection = new ConnectionFactoryDAO();
        Statement stmt = null;

        try {

            stmt = connection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if(!rs.isBeforeFirst()){
                return null;
            }

            else{

                while (rs.next()) {
                    String aux = rs.getString(1).trim();
                    if(!StringUtils.isEmpty(aux)) {
                        resposta = aux;
                    }
                }

            }

            rs.close();
            stmt.close();
            connection.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                connection.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resposta;
    }

    public ArrayList<String> returnNameIDInSearchProjectIDPerNameInMantisProjectTable(String nome){
        String query = "SELECT id, name FROM bugtracker.mantis_project_table WHERE name = '$nome';".replace("$nome",nome);
        ArrayList<String> arrayList = new ArrayList<>();
        ConnectionFactoryDAO connection = new ConnectionFactoryDAO();
        Statement stmt = null;

        try {

            stmt = connection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if(!rs.isBeforeFirst()){
                return null;
            }
            else{
                while (rs.next()) {
                    arrayList.add(rs.getString(1).trim());
                    arrayList.add(rs.getString(2).trim());
                }
            }

            rs.close();
            stmt.close();
            connection.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                connection.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public Map<String, Object> getProjectForName(String nome) throws Exception {
        String sql = "SELECT * FROM projeto WHERE NAME ='"+nome+"';";
        Map<String,Object> map = new HashMap<>();
        ConnectionFactoryDAO connection = new ConnectionFactoryDAO(ConnectionFactoryDAO.CONNECTION_DADOS_DE_TESTE);
        Statement stmt = null;

        try {

            stmt = connection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if(!rs.isBeforeFirst()){
                return null;
            }

            else{

                if (rs.next()) {
                    int i = 1;
                    map.put("name",rs.getString(i++));
                    map.put("status",rs.getInt(i++));
                    map.put("enabled",rs.getInt(i++));
                    map.put("viewState",rs.getInt(i++));
                    map.put("accessMin",rs.getInt(i++));
                    map.put("description",rs.getString(i++));
                    map.put("categoryID",rs.getInt(i));
                    map.put("inheritGlobal",rs.getInt(i));
                }
            }
            LOGGER.info("ESSA POORRRA DE INSERIR NOMP = "+map.get("name").toString());
            rs.close();
            stmt.close();
            connection.closeConnection();

        } catch (SQLException errorsql) {
            throw new Exception("Falha ocorrida Connection Factory: "+errorsql.getMessage());
        } finally{
            try {
                connection.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public String getIDProject(String nome) throws Exception {
        String query = "SELECT id FROM bugtracker.mantis_project_table WHERE name LIKE '"+nome+"';";
        String resposta=null;
        ConnectionFactoryDAO connection = new ConnectionFactoryDAO();
        Statement stmt = null;

        try {

            stmt = connection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if(!rs.isBeforeFirst()){
                return null;
            } else{
                if (rs.next()) {
                    resposta = rs.getString(2);
                }
            }
            rs.close();
            stmt.close();
            connection.closeConnection();

        } catch (Exception errorsql) {
            System.out.println("Falha ocorrida Connection Factory: "+errorsql.getMessage());
        } finally{
            try {
                connection.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resposta;
    }

    public Map<String, Object> getUserForName(String username) throws Exception {
        String sql = "SELECT * FROM usuario WHERE USERNAME ='"+username+"';";
        Map<String,Object> map = new HashMap<>();
        ConnectionFactoryDAO connection = new ConnectionFactoryDAO(ConnectionFactoryDAO.CONNECTION_DADOS_DE_TESTE);
        Statement stmt = null;

        try {

            stmt = connection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if(!rs.isBeforeFirst()){
                return null;
            }

            else{

                if (rs.next()) {
                    int i = 1;
                    map.put("username",rs.getString(i++));
                    map.put("realname",rs.getString(i++));
                    map.put("email",rs.getString(i++));
                    map.put("enabled",rs.getInt(i++));
                    map.put("protected",rs.getInt(i++));
                    map.put("acesseLevel",rs.getInt(i++));
                    map.put("cookieString",rs.getString(i++));
                    map.put("tipoUsuario",rs.getString(i));
                }
            }
            rs.close();
            stmt.close();
            connection.closeConnection();

        } catch (SQLException errorsql) {
            throw new Exception("Falha ocorrida Connection Factory: "+errorsql.getMessage());
        } finally{
            try {
                connection.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public Map<String, Object> getUserForType(String tipoUsuario) throws Exception {
        String sql = "SELECT * FROM usuario WHERE TIPO_USUARIO ='"+tipoUsuario+"';";
        Map<String,Object> map = new HashMap<>();
        ConnectionFactoryDAO connection = new ConnectionFactoryDAO(ConnectionFactoryDAO.CONNECTION_DADOS_DE_TESTE);
        Statement stmt = null;

        try {

            stmt = connection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if(!rs.isBeforeFirst()){
                return null;
            }

            else{

                if (rs.next()) {
                    int i = 1;
                    map.put("username",rs.getString(i++));
                    map.put("realname",rs.getString(i++));
                    map.put("email",rs.getString(i++));
                    map.put("enabled",rs.getInt(i++));
                    map.put("protected",rs.getInt(i++));
                    map.put("acesseLevel",rs.getInt(i++));
                    map.put("cookieString",rs.getString(i++));
                    map.put("tipoUsuario",rs.getString(i));
                }
            }
            rs.close();
            stmt.close();
            connection.closeConnection();

        } catch (SQLException errorsql) {
            throw new Exception("Falha ocorrida Connection Factory: "+errorsql.getMessage());
        } finally{
            try {
                connection.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public UsuariosType getUsuarioDadosTesteUsuarioTable(String tipoUsuario){
        UsuariosType usuariosType = new UsuariosType();
        ConnectionFactoryDAO connection = null;
        try {
            String sql = "SELECT * FROM DADOS_TESTE_API.usuario WHERE USERNAME = '" + tipoUsuario + "';";
            connection = new ConnectionFactoryDAO(ConnectionFactoryDAO.CONNECTION_DADOS_DE_TESTE);
            Statement stmt = null;
            stmt = connection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.isBeforeFirst()) {
                return null;
            } else {
                if (rs.next()) {
                    usuariosType.setUserName(rs.getString(1));
                    usuariosType.setRealName(rs.getString(2));
                    usuariosType.setEmail(rs.getString(3));
                    usuariosType.setEnabled(rs.getString(4));
                    usuariosType.setProtekted(rs.getString(5));
                    usuariosType.setAccessLevel(rs.getString(6));
                    usuariosType.setCookieString(rs.getString(7));
                    usuariosType.setPassword(rs.getString(8));
                    usuariosType.setTipoCargo(rs.getString(9));
                }
            }
            rs.close();
            stmt.close();
            connection.closeConnection();

        } catch (Exception ex) {
            System.out.println("Falha ocorrida Connection Factory: " + ex.getMessage());
        }

        return usuariosType;
    }

    public UsuariosType getUsuarioDadosEmailTesteUsuarioTable(String emailUsuario){
        UsuariosType usuariosType = new UsuariosType();
        ConnectionFactoryDAO connection = null;
        try {
            String sql = "SELECT * FROM DADOS_TESTE_API.usuario WHERE EMAIL = '" + emailUsuario + "';";
            connection = new ConnectionFactoryDAO(ConnectionFactoryDAO.CONNECTION_DADOS_DE_TESTE);
            Statement stmt = null;
            stmt = connection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.isBeforeFirst()) {
                return null;
            } else {
                if (rs.next()) {
                    usuariosType.setUserName(rs.getString(1));
                    usuariosType.setRealName(rs.getString(2));
                    usuariosType.setEmail(rs.getString(3));
                    usuariosType.setEnabled(rs.getString(4));
                    usuariosType.setProtekted(rs.getString(5));
                    usuariosType.setAccessLevel(rs.getString(6));
                    usuariosType.setCookieString(rs.getString(7));
                    usuariosType.setPassword(rs.getString(8));
                    usuariosType.setTipoCargo(rs.getString(9));
                }
            }
            rs.close();
            stmt.close();
            connection.closeConnection();

        } catch (Exception ex) {
            System.out.println("Falha ocorrida Connection Factory: " + ex.getMessage());
        }

        return usuariosType;
    }

    public String getIDUsuarioMantisBTUserTable(String tipoUsuario){
        ConnectionFactoryDAO connection = null;
        try {
            String sql = "SELECT id FROM bugtracker.mantis_user_table WHERE username = '" + tipoUsuario + "';";
            connection = new ConnectionFactoryDAO(ConnectionFactoryDAO.CONNECTION_DADOS_DE_TESTE);
            Statement stmt = null;
            stmt = connection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.isBeforeFirst()) {
                return null;
            } else {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
            rs.close();
            stmt.close();
            connection.closeConnection();

        } catch (Exception ex) {
            System.out.println("Falha ocorrida Connection Factory: " + ex.getMessage());
        }

        return null;
    }

    public UsuariosType getUsuarioNomeRealDadosTesteUsuarioTable(String nomeReal){
        UsuariosType usuariosType = new UsuariosType();
        ConnectionFactoryDAO connection = null;
        try {
            String sql = "SELECT * FROM DADOS_TESTE_API.usuario WHERE REALNAME = '"+nomeReal+"';";
            connection = new ConnectionFactoryDAO(ConnectionFactoryDAO.CONNECTION_DADOS_DE_TESTE);
            Statement stmt = null;
            stmt = connection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.isBeforeFirst()) {
                return null;
            } else {
                if (rs.next()) {
                    usuariosType.setUserName(rs.getString(1));
                    usuariosType.setRealName(rs.getString(2));
                    usuariosType.setEmail(rs.getString(3));
                    usuariosType.setEnabled(rs.getString(4));
                    usuariosType.setProtekted(rs.getString(5));
                    usuariosType.setAccessLevel(rs.getString(6));
                    usuariosType.setCookieString(rs.getString(7));
                    usuariosType.setPassword(rs.getString(8));
                    usuariosType.setTipoCargo(rs.getString(9));
                }
            }
            rs.close();
            stmt.close();
            connection.closeConnection();

        } catch (Exception ex) {
            System.out.println("Falha ocorrida getUsuarioNomeRealDadosTesteUsuarioTable : " + ex.getMessage());
        }

        return usuariosType;
    }

    public UsuarioTestBody getUsuarioTestBodyMantisBTTableUsuario(){
        UsuarioTestBody usuariosTestBody = new UsuarioTestBody();
        ConnectionFactoryDAO connection = null;
        try {
            String sql = "SELECT id,username,realname,email,password,enabled,protected,access_level FROM bugtracker.mantis_user_table Where id = 1;";
            connection = new ConnectionFactoryDAO();
            Statement stmt = null;
            stmt = connection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.isBeforeFirst()) {
                return null;
            } else {
                if (rs.next()) {
                    usuariosTestBody.setId(rs.getInt(1));
                    usuariosTestBody.setUsername(rs.getString(2));
                    usuariosTestBody.setRealname(rs.getString(3));
                    usuariosTestBody.setEmail(rs.getString(4));
                    usuariosTestBody.setPassword(rs.getString(5));
                    usuariosTestBody.setEnabled(rs.getInt(6));
                    usuariosTestBody.setProtecteD(rs.getInt(7));
                    usuariosTestBody.setAccessLevel(rs.getInt(8));
                }
            }
            rs.close();
            stmt.close();
            connection.closeConnection();

        } catch (Exception ex) {
            System.out.println("Falha ocorrida getUsuarioTestBodyMantisBTTableUsuario : " + ex.getMessage());
        }

        return usuariosTestBody;
    }

    public Map<String, Object> getUserForEmail(String email) throws Exception {
        String sql = "SELECT * FROM usuario WHERE email ='"+email+"';";
        Map<String,Object> map = new HashMap<>();
        ConnectionFactoryDAO connection = new ConnectionFactoryDAO(ConnectionFactoryDAO.CONNECTION_DADOS_DE_TESTE);
        Statement stmt = null;

        try {

            stmt = connection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if(!rs.isBeforeFirst()){
                return null;
            }

            else{

                if (rs.next()) {
                    int i = 1;
                    map.put("username",rs.getString(i++));
                    map.put("realname",rs.getString(i++));
                    map.put("email",rs.getString(i++));
                    map.put("enabled",rs.getInt(i++));
                    map.put("protected",rs.getInt(i++));
                    map.put("acesseLevel",rs.getInt(i++));
                    map.put("cookieString",rs.getString(i++));
                    map.put("tipoUsuario",rs.getString(i));
                }
            }
            rs.close();
            stmt.close();
            connection.closeConnection();

        } catch (SQLException errorsql) {
            throw new Exception("Falha ocorrida Connection Factory: "+errorsql.getMessage());
        } finally{
            try {
                connection.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public String selectAndDeleteIfExistBugText(String nome){
        ArrayList<Integer> list = new ArrayList<>();
        String query = "SELECT id FROM bugtracker.mantis_bug_text_table WHERE steps_to_reproduce = '$nome'".replace("$nome",nome);
        String resposta = null;
        ConnectionFactoryDAO connection = new ConnectionFactoryDAO();
        Statement stmt = null;
        try {
            stmt = connection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst()){
                resposta = String.valueOf(0);
            }else{
                while (rs.next()) {
                    list.add(rs.getInt(1));
                }
            }
            rs.close();
            stmt.close();
            connection.closeConnection();

            DeleteDAO deleteDao = new DeleteDAO();
            if(list.size() > 0){
                for(Integer loop: list){
                    deleteDao.deleteBUGTextMantisBTPerIDProject(loop);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                connection.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resposta;
    }

    public String selectAndDeleteIfExistMantidBTProject(String nome){
        ArrayList<Integer> list = new ArrayList<>();
        String query = "SELECT id FROM bugtracker.mantis_project_table WHERE name = '$nome'".replace("$nome",nome);
        String resposta = null;
        ConnectionFactoryDAO connection = new ConnectionFactoryDAO();
        Statement stmt = null;
        try {
            stmt = connection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst()){
                resposta = String.valueOf(0);
            }else{
                while (rs.next()) {
                    list.add(rs.getInt(1));
                }
            }
            rs.close();
            stmt.close();
            connection.closeConnection();

            DeleteDAO deleteDao = new DeleteDAO();
            if(list.size() > 0){
                for(Integer loop: list){
                    deleteDao.deleteProjectMantisBTPerIDProject(loop);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                connection.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resposta;
    }

    public String selectAndDeleteIfExistMantidBTFile(String fileName){
        ArrayList<Integer> list = new ArrayList<>();
        String query = "SELECT id FROM bugtracker.mantis_bug_file_table WHERE filename = '$filename'".replace("$filename",fileName);
        String resposta = null;
        ConnectionFactoryDAO connection = new ConnectionFactoryDAO();
        Statement stmt = null;
        try {
            stmt = connection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst()){
                resposta = String.valueOf(0);
            }else{
                while (rs.next()) {
                    list.add(rs.getInt(1));
                }
            }
            rs.close();
            stmt.close();
            connection.closeConnection();

            DeleteDAO deleteDao = new DeleteDAO();
            if(list.size() > 0){
                for(Integer loop: list){
                    deleteDao.deleteFileMantisBTPerID(loop);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                connection.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resposta;
    }

    public String selectRequestJsonBody(String nomeRequest){
        String query = "SELECT request_body FROM DADOS_TESTE_API.json_body_request WHERE nome_requisicao = '$nome';".replace("$nome", nomeRequest);
        String resposta = null;
        ConnectionFactoryDAO connection = new ConnectionFactoryDAO();
        Statement stmt = null;
        try {
            stmt = connection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst()){
                return null;
            }else{
                while(rs.next()) {
                    String aux = rs.getString(1).trim();
                    if(!StringUtils.isEmpty(aux)) {
                        resposta = aux;
                    }
                }
            }
            rs.close();
            stmt.close();
            connection.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                connection.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resposta;
    }

    public int getQuantidadeDeProjetosCadastradosNoMantiBT(){
        ArrayList<Integer> list = new ArrayList<>();
        String query = "SELECT COUNT(*) FROM bugtracker.mantis_project_table;";
        int resposta = 0;
        ConnectionFactoryDAO connection = new ConnectionFactoryDAO();
        Statement stmt = null;
        try {
            stmt = connection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst()){
                resposta = Integer.parseInt(String.valueOf(0));
            }else{
                while (rs.next()) {
                    resposta = rs.getInt(1);
                }
            }
            rs.close();
            stmt.close();
            connection.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                connection.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resposta;
    }
}
