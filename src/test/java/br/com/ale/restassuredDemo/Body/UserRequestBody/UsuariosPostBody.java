package br.com.ale.restassuredDemo.Body.UserRequestBody;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class UsuariosPostBody {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("real_name")
    private String real_name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("access_level")
    AccessLevelPostBody accessLevelPostBody;
    @JsonProperty("enabled")
    private boolean enabled;
    @JsonProperty("protected")
    private boolean protectedVariable;

    public UsuariosPostBody(String username, String password, String real_name, String email, AccessLevelPostBody accessLevelPostBody, boolean enabled, boolean protectedVariable) {
        this.username = username;
        this.password = password;
        this.real_name = real_name;
        this.email = email;
        this.accessLevelPostBody = accessLevelPostBody;
        this.enabled = enabled;
        this.protectedVariable = protectedVariable;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getReal_name() {
        return real_name;
    }

    public String getEmail() {
        return email;
    }

    public AccessLevelPostBody getAccessLevelPostBody() {
        return accessLevelPostBody;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public boolean getProtectedVariable() {
        return protectedVariable;
    }

    // Setter Methods

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccessLevelPostBody(AccessLevelPostBody accessLevelPostBody) {
        this.accessLevelPostBody = accessLevelPostBody;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setProtectedVariable(boolean protectedVariable) {
        this.protectedVariable = protectedVariable;
    }


    //Monta um map , o nome das chaves é passada pelo metodo que le o nome das variveis da classe passada
    //Retorna um Map Com os nome das variaveis como chaves e valores
    public Map<String, Object> getDeclaredFields(UsuariosPostBody userPostBody) throws SecurityException{
        Map<String, Object> map = new HashMap<>();
        try {
            //Responsavel por analisar a classe e guardar essas informações em uma lista
            BeanInfo beanInfo = Introspector.getBeanInfo(UsuariosPostBody.class);
            for(PropertyDescriptor propertyDescriptor: beanInfo.getPropertyDescriptors()){
                //Retorna os nome das variveis utilizadas na classe
                String propertyName = propertyDescriptor.getName();
                //Retorna os gets da classe passada no parametro
                Object propertyValue = propertyDescriptor.getReadMethod().invoke(userPostBody);
                //O metodo retorna uma variavel class por isso é necessario filtrar o retorno
                if(!propertyName.equalsIgnoreCase("class")){
                    map.put(propertyName, propertyValue);
                }
            }
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
