 package br.com.ale.restassuredDemo.utils;

import br.com.ale.restassuredDemo.Types.UsuariosType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.codec.binary.Base64;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

 public class UtilsQuery {
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilsQuery.class);

    public static ArrayList<String> lerSQL(File file){
        ArrayList<String> arrayList = new ArrayList<>();
        try{
            Scanner sc = new Scanner(file);
            sc.useDelimiter(";");
            while (sc.hasNext()){
                arrayList.add(sc.next().trim());
            }
            sc.close();
        }catch (Exception ex){
            System.out.println("LerSQL : "+ex.getMessage());
        }
        return arrayList;
    }

    public static String retornarAnoMesDiaAtual(){
        Calendar calendar = Calendar.getInstance();
        return ""+calendar.get(Calendar.YEAR)+calendar.get(Calendar.MONTH)+calendar.get(Calendar.DAY_OF_MONTH);
    }

     public static String encodeFileToBase64Binary(String caminho) {
         File file = new File(caminho);
         String encodedfile = null;
         try {
             FileInputStream fileInputStreamReader = new FileInputStream(file);
             byte[] bytes = new byte[(int) file.length()];
             fileInputStreamReader.read(bytes);
             encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
         return encodedfile;
     }

     public static String getMethodNameByLevel(int level){
         StackTraceElement[] stackTrace = new Throwable().getStackTrace();
         return stackTrace[level].getMethodName();
     }

     public static int getDataEpochTime(){
         return Integer.parseInt(String.valueOf((new Date().getTime()/1000)));
     }

}
