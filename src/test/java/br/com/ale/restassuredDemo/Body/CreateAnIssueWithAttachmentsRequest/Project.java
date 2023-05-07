package br.com.ale.restassuredDemo.Body.CreateAnIssueWithAttachmentsRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Project {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;


    public long getID() { return id; }

    public String getName(){return name;}
    public void setID(long value) { this.id = value; }

    public void setName(String name){
        this.name = name;
    }
}
