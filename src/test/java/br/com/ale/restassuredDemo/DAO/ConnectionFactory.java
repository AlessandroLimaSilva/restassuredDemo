package br.com.ale.restassuredDemo.DAO;

import br.com.ale.restassuredDemo.utils.GlobalParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionFactory.class);
    private static Connection CONNECTION;
    protected static final String CONNECTION_BUG_TRACKER = GlobalParameters.DB_URL_MANTISBT;
    protected static final String CONNECTION_DADOS_DE_TESTE = GlobalParameters.DB_URL_MANTISBT;
    private String usuarioBanco = GlobalParameters.DB_USER;
    private String senhaBanco = GlobalParameters.DB_PASSWORD;

    public ConnectionFactory(){
        try {
            if (CONNECTION != null && !CONNECTION.isClosed()) return;
            Class.forName("com.mysql.cj.jdbc.Driver");
            CONNECTION = DriverManager.getConnection(CONNECTION_BUG_TRACKER,usuarioBanco,senhaBanco);
            CONNECTION.setAutoCommit(false);
            CONNECTION.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public ConnectionFactory(String enderecoBanco, String nomeUsuario, String senha){
        try {
            if (CONNECTION != null && !CONNECTION.isClosed()) return;
            Class.forName("com.mysql.cj.jdbc.Driver");
            CONNECTION = DriverManager.getConnection(enderecoBanco,nomeUsuario, senha);
            CONNECTION.setAutoCommit(false);
            CONNECTION.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public ConnectionFactory(String url){
        try {
            if (CONNECTION != null && !CONNECTION.isClosed()){
                CONNECTION.close();
                Class.forName("com.mysql.cj.jdbc.Driver");
                CONNECTION = DriverManager.getConnection(url,usuarioBanco, senhaBanco);
                CONNECTION.setAutoCommit(false);
                CONNECTION.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                LOGGER.info("Connection is Open");
            }else {
                Class.forName("com.mysql.cj.jdbc.Driver");
                CONNECTION = DriverManager.getConnection(url,usuarioBanco, senhaBanco);
                CONNECTION.setAutoCommit(false);
                CONNECTION.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                LOGGER.info("Connection is Open");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public Connection getConnection(){
        return CONNECTION;
    }

    public void closeConnection(){
        try{

            if (CONNECTION == null || CONNECTION.isClosed()){
                return;
            }else {
                CONNECTION.close();
            }

            LOGGER.info("Connection is Close");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void transactionConfirm(){
        try{
            if (CONNECTION == null || CONNECTION.isClosed()){
                return;
            }else{
                CONNECTION.commit();
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void transactionCancel(){
        try {
            if (CONNECTION == null || CONNECTION.isClosed()){
                return;
            }else {
                CONNECTION.rollback();
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
