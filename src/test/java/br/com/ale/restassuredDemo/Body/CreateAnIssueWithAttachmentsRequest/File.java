package br.com.ale.restassuredDemo.Body.CreateAnIssueWithAttachmentsRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class File {
    @JsonProperty("name")
    private String name;
    @JsonProperty("content")
    private String content;

    public void setName(String value) { this.name = value; }
    public String getName() { return name; }

    public void setContent(String value) { this.content = value; }
    public String getContent() { return content; }
}
