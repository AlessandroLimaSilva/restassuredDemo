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

    public static String lerConteudoSQL(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        sc.useDelimiter("\\A");
        String sql = sc.hasNext() ? sc.next() : "";
        sc.close();
        return sql;
    }

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

    public static ArrayList<UsuariosType> usuariosList(String file) {
        ArrayList<UsuariosType> usuarioTypeList;
        try {
            Scanner sc = new Scanner(file);
            usuarioTypeList = new ArrayList<UsuariosType>();
            while (sc.hasNextLine()) {
                ArrayList<String> listaUsuario = new ArrayList<String>(Arrays.asList(sc.nextLine().split(";")));
                UsuariosType usuariosType = new UsuariosType(listaUsuario.get(0),
                        listaUsuario.get(1),
                        listaUsuario.get(2),
                        listaUsuario.get(3),
                        listaUsuario.get(4),
                        listaUsuario.get(5),
                        listaUsuario.get(6),
                        listaUsuario.get(7),
                        listaUsuario.get(8));
                usuarioTypeList.add(usuariosType);
            }
            sc.close();
            for (UsuariosType type : usuarioTypeList) {
                JOptionPane.showMessageDialog(null, "Usuario : \n" +
                        "UserName : " + type.getUserName() +
                        "\nRealName : " + type.getRealName() +
                        "\nEmail : " + type.getEmail() +
                        "\nEnabled : " + type.getEnabled() +
                        "\nProtected : " + type.getProtekted() +
                        "\nAccess Level : " + type.getAccessLevel() +
                        "\nCookie String : " + type.getCookieString() +
                        "\nPassword : " + type.getPassword() +
                        "\nTipo Cargo : " + type.getTipoCargo());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return usuarioTypeList;
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

     /* Investigar este metodo ele retorna os resultados dos teste quando utilizado o testng
     public static String getAllStackTrace(ITestResult result){
         String allStackTrace = "";

         for(StackTraceElement stackTrace : result.getThrowable().getStackTrace()){
             allStackTrace = allStackTrace + "<br>" + stackTrace.toString();
         }

         return allStackTrace;
     }*/
}
