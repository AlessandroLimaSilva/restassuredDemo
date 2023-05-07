package br.com.ale.restassuredDemo.Body.IssueCreateAnIssueMinimalRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Project {
    @JsonProperty("id")
    private long id;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }
}
