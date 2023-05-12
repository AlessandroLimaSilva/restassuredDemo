package br.com.ale.restassuredDemo.Body.UserRequestBody;

public class AccessLevelPostBody {

    private String name;
    private String id;
    private String label;

    public AccessLevelPostBody(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public String getId(){
        return id;
    }

    public String getLabel(){
        return label;
    }
}
