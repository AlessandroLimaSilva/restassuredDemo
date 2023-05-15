package br.com.ale.restassuredDemo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class GlobalParameters {

    public static String ENVIROMENT;
    public static String URL_DEFAULT;
    public static String REPORT_NAME;
    public static String REPORT_PATH;
    public static String DB_URL_TESTE;
    public static String DB_URL_MANTISBT;
    public static String DB_NAME;
    public static String DB_USER;
    public static String DB_PASSWORD;
    public static String URL_TOKEN;
    public static String TOKEN;
    public static String AUTHENTICATOR_USER;
    public static String AUTHENTICATOR_PASSWORD;
    public static List<String> fileContent;

    static {
        Properties properties = new Properties();
        InputStream inputStream = null;

        try{
            inputStream = Files.newInputStream(Paths.get("src/globalParameters.propeties"));
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        REPORT_NAME = properties.getProperty("report.name");
        REPORT_PATH = properties.getProperty("report.path");
        ENVIROMENT = properties.getProperty("enviroment");

        if(ENVIROMENT.equals("hml")){
            DB_URL_MANTISBT = properties.getProperty("hml.db.url.mantis");
            DB_URL_TESTE = properties.getProperty("hml.db.url.teste");
            DB_NAME = properties.getProperty("hml.db.name");
            DB_USER = properties.getProperty("hml.db.user");
            DB_PASSWORD = properties.getProperty("hml.db.password");
            URL_DEFAULT = properties.getProperty("hml.url.default");
            URL_TOKEN = properties.getProperty("hml.url.token");
            TOKEN = properties.getProperty("hml.token");
            AUTHENTICATOR_USER = properties.getProperty("hml.autenticator.user");
            AUTHENTICATOR_PASSWORD = properties.getProperty("hml.autenticator.password");
        }

        if(ENVIROMENT.equals("dev")){
            DB_URL_MANTISBT = properties.getProperty("dev.db.url.mantis");
            DB_URL_TESTE = properties.getProperty("dev.db.url.teste");
            DB_NAME = properties.getProperty("dev.db.name");
            DB_USER = properties.getProperty("dev.db.user");
            DB_PASSWORD = properties.getProperty("dev.db.password");
            URL_DEFAULT = properties.getProperty("dev.url.default");
            URL_TOKEN = properties.getProperty("dev.url.token");
            TOKEN = properties.getProperty("dev.token");
            AUTHENTICATOR_USER = properties.getProperty("dev.autenticator.user");
            AUTHENTICATOR_PASSWORD = properties.getProperty("dev.autenticator.password");
        }
    }

    public static void setToken(String dado){
        TOKEN = dado;
    }
}
