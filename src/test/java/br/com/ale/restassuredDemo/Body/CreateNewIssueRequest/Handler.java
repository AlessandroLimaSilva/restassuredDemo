package br.com.ale.restassuredDemo.Body.CreateNewIssueRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Handler {
    @JsonProperty("name")
    private String name;

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }
}
