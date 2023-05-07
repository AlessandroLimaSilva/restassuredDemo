package br.com.ale.restassuredDemo.Body.CreateAProjectPostRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Status {
    @JsonProperty("name")
    private String name;

    public String getName() { return name; }

    public void setName(String value) { this.name = value; }
}
