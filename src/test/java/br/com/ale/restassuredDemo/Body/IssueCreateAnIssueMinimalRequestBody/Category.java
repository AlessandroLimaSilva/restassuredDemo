package br.com.ale.restassuredDemo.Body.IssueCreateAnIssueMinimalRequestBody;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {
    @JsonProperty("name")
    private String name;

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }
}
