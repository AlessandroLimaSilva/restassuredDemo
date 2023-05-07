package br.com.ale.restassuredDemo.Body.UserBody;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuariosGETBody {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
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

    public UsuariosGETBody(String id, String username, String password, String real_name, String email, AccessLevelPostBody accessLevelPostBody, boolean enabled, boolean protectedVariable) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.real_name = real_name;
        this.email = email;
        this.accessLevelPostBody = accessLevelPostBody;
        this.enabled = enabled;
        this.protectedVariable = protectedVariable;
    }

    // Getter Methods

    public String getID(){
        return id;
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

    public void setId(String id){
        this.id = id;
    }

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
}
