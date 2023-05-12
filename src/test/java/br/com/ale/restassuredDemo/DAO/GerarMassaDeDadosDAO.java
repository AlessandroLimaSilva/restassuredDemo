package br.com.ale.restassuredDemo.DAO;

import br.com.ale.restassuredDemo.utils.UtilsQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

public class GerarMassaDeDadosDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionFactory.class);
    private String connectionBD = "jdbc:mysql://localhost/";
    private String connectionDadosDeTeste = "jdbc:mysql://localhost/dadosdeteste";
    private String usuarioBanco = "root";
    private String senhaBanco = "root";
    private static String dado = "src/test/resources/query/MassaDeTeste.sql";
    private String massaDeTeste = "src/test/resources/query/MassaDeTeste.sql";
    private String massaTesteApi = "src/test/resources/query/montarBancoTeste.sql";

    private String verificaSeBancoExiste = "DROP DATABASE IF EXISTS dadosdeteste;";
    private String criaOBancoDeDados = "CREATE DATABASE dadosdeteste;";

    private String criarTabelaProjeto = "src/test/resources/query/CreateTableProjeto.sql";
    private String criarTabelaUsuario = "src/test/resources/query/CreateTableUsuario.sql";
    private String insertTodosProjetos = "src/test/resources/query/InsertTodosProjetos.sql";
    private String insertTodosUsuarios = "src/test/resources/query/InsertTodosUsuarios.sql";


    public GerarMassaDeDadosDAO(){

    }

    public void create(){
        try{
            ArrayList<String> sqlList = criarlistaSQL(massaTesteApi);
            for(String sql: sqlList){
                InsertDAO insertDAO = new InsertDAO();
                insertDAO.setDataInsert(sql,connectionBD,usuarioBanco,senhaBanco);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void criarDadosDeTeste() throws Exception {

        ArrayList<String> novaListaSQL = listaSQL();
        for (String sql: novaListaSQL){
            LOGGER.info(sql);
            if (sql.contains("DATABASE")){
                InsertDAO insertDAO = new InsertDAO();
                insertDAO.setDataInsert(sql,connectionBD,usuarioBanco,senhaBanco);
            }else{
                InsertDAO insertDAO = new InsertDAO();
                insertDAO.setDataInsert(sql,connectionDadosDeTeste,usuarioBanco,senhaBanco);
            }
        }
        LOGGER.info("Banco de dados com os dados de teste OK");
    }

    public void testeDoCaralho() throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory(connectionBD,usuarioBanco,senhaBanco);
        Statement stmt = connectionFactory.getConnection().createStatement();
        for (String sql: listaSQL()){
            LOGGER.info(sql);
            stmt.execute(sql);
        }
        connectionFactory.transactionConfirm();
        connectionFactory.closeConnection();
    }

    public void connectionStatementExecute(String pathDB,String sql) throws Exception {
        try{
            String url = "jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=convertToNull";
            Connection connection = DriverManager.getConnection(url,usuarioBanco, senhaBanco);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        }catch (Exception ex) {
            throw new Exception("Falha ocorrida connectionStatementExecute: "+sql+"\n"+ex.getMessage());
        }

        /*
        try{
            LOGGER.info(pathDB);
            ConnectionFactory connectionFactory = new ConnectionFactory(pathDB,usuarioBanco,senhaBanco);
            Statement stmt = connectionFactory.getConnection().createStatement();
            stmt.executeUpdate(sql);
            connectionFactory.transactionConfirm();

            stmt.close();
            connectionFactory.closeConnection();

        } catch (Exception ex) {
            throw new Exception("Falha ocorrida deletarBancoDeDadosSeExistir: "+ex.getMessage());
        }

         */
    }

    public void setDeletarBancoDeDadosSeExistir() throws Exception {
        connectionStatementExecute(connectionDadosDeTeste,verificaSeBancoExiste);
    }

    public void setCriarBancoDeDados() throws Exception {
        connectionStatementExecute(connectionBD,criaOBancoDeDados);
    }


    public void setCriarTabelaProjeto() throws Exception {
        InsertDAO insertDAO = new InsertDAO();
        insertDAO.setDataInsert(getSQL(criarTabelaProjeto),connectionDadosDeTeste,usuarioBanco,senhaBanco);
    }

    public void setCriarTabelaUsuario() throws Exception {
        InsertDAO insertDAO = new InsertDAO();
        insertDAO.setDataInsert(getSQL(criarTabelaUsuario),connectionDadosDeTeste,usuarioBanco,senhaBanco);
    }

    public void setInsertTodosProjetos() throws Exception {
        InsertDAO insertDAO = new InsertDAO();
        insertDAO.setDataInsert(getSQL(insertTodosProjetos),connectionDadosDeTeste,usuarioBanco,senhaBanco);
    }
    public void setInsertTodosUsuarios() throws Exception {
        InsertDAO insertDAO = new InsertDAO();
        insertDAO.setDataInsert(getSQL(insertTodosUsuarios),connectionDadosDeTeste,usuarioBanco,senhaBanco);
    }

    public ArrayList<String> listaSQL() throws Exception {
        ArrayList<String> lista = UtilsQuery.lerSQL(new File(massaDeTeste));
        return lista;
    }

    public ArrayList<String> criarlistaSQL(String arquivoSQL) throws Exception {
        ArrayList<String> lista = UtilsQuery.lerSQL(new File(arquivoSQL));
        return lista;
    }

    public String getSQL(String path){
        String sql="";
        try{
            sql = UtilsQuery.lerConteudoSQL(new File(path));
            return sql;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sql;
    }
}
