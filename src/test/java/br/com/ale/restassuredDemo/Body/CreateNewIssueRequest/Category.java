package br.com.ale.restassuredDemo.Body.CreateNewIssueRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }
}
