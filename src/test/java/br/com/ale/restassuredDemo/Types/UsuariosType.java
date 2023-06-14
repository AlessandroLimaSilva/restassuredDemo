package br.com.ale.restassuredDemo.Types;


public class UsuariosType {

    private String userName;
    private String realName;
    private String email;
    private String enabled;
    private String protekted;
    private String accessLevel;
    private String cookieString;
    private String password;
    private String tipoCargo;

    public UsuariosType(String userName, String realName, String email, String enabled, String protekted, String accessLevel, String cookieString, String password, String tipoCargo){
        this.userName = userName;
        this.realName = realName;
        this.email = email;
        this.enabled = enabled;
        this.protekted = protekted;
        this.accessLevel = accessLevel;
        this.cookieString = cookieString;
    }

    public UsuariosType(){

    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setRealName(String realName){
        this.realName = realName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setEnabled(String enabled){
        this.enabled = enabled;
    }

    public void setProtekted(String protekted){
        this.protekted = protekted;
    }

    public void setAccessLevel(String accessLevel){
        this.accessLevel = accessLevel;
    }

    public void setCookieString(String cookieString){
        this.cookieString = cookieString;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setTipoCargo(String tipoCargo){
        this.tipoCargo = tipoCargo;
    }

    public String getUserName(){
        return userName;
    }

    public String getRealName(){
        return realName;
    }

    public String getEmail(){
        return email;
    }

    public String getEnabled(){
        return enabled;
    }

    public boolean getEnableBoolean(){
        return !enabled.equals("0");
    }
    public String getProtekted(){
        return protekted;
    }

    public boolean getProtektedBoolean(){
        return !protekted.equals("0");
    }

    public String getAccessLevel(){
        return accessLevel;
    }

    public String getCookieString(){
        return cookieString;
    }

    public String getPassword(){
        return password;
    }

    public String getTipoCargo(){
        return tipoCargo;
    }
}
