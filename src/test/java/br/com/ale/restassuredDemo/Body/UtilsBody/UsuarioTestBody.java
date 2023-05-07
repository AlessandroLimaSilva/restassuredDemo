package br.com.ale.restassuredDemo.Body.UtilsBody;

public class UsuarioTestBody {

    private int id;
    private String username;
    private String realname;
    private String email;
    private String password;
    private int enabled;
    private int protecteD;
    private int accessLevel;

    public UsuarioTestBody(int id, String username,String realname,String email,String password,int enabled,int protecteD,int accessLevel){
        this.id = id;
        this.username = username;
        this.realname = realname;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.protecteD = protecteD;
        this.accessLevel = accessLevel;
    }

    public UsuarioTestBody(){}

    public void setId(int id){
        this.id = id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setRealname(String realname){
        this.realname = realname;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setEnabled(int enabled){
        this.enabled = enabled;
    }

    public void setProtecteD(int protecteD){
        this.protecteD = protecteD;
    }

    public void setAccessLevel(int accessLevel){
        this.accessLevel = accessLevel;
    }

    public int getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public String getRealname(){
        return realname;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public int getEnabled(){
        return enabled;
    }

    public int getProtecteD(){
        return protecteD;
    }

    public int getAccessLevel(){
        return accessLevel;
    }

}
