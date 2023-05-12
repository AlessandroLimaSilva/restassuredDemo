package br.com.ale.restassuredDemo.Body.CreateNewIssueRequestBody;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomField {
    @JsonProperty("field")
    private Category field;
    @JsonProperty("value")
    private String value;

    public Category getField() { return field; }
    public void setField(Category value) { this.field = value; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}
