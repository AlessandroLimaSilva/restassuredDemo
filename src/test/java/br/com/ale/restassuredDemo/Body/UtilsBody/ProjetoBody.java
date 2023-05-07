package br.com.ale.restassuredDemo.Body.UtilsBody;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjetoBody {

    @JsonProperty("name")
    private String name;
    @JsonProperty("status")
    private int status;
    @JsonProperty("enabled")
    private int enabled;
    @JsonProperty("view_state")
    private int viewState;
    @JsonProperty("access_min")
    private int accessMin;
    @JsonProperty("description")
    private String description;
    @JsonProperty("category_id")
    private int categoryID;
    @JsonProperty("inherit_global")
    private int inheritGlobal;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public int getStatus(){
        return status;
    }

    public void setEnabled(int enabled){
        this.enabled = enabled;
    }

    public int getEnabled(){
        return enabled;
    }

    public void setViewState(int viewState){
        this.viewState = viewState;
    }

    public int getViewState(){
        return viewState;
    }

    public void setAccessMin(int accessMin){
        this.accessMin = accessMin;
    }

    public int getAccessMin(){
        return accessMin;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return  description;
    }

    public void setCategoryID(int categoryID){
        this.categoryID = categoryID;
    }

    public int getCategoryID(){
        return categoryID;
    }

    public void setInheritGlobal(int inheritGlobal){
        this.inheritGlobal = inheritGlobal;
    }

    public int getInheritGlobal(){
        return inheritGlobal;
    }
}
