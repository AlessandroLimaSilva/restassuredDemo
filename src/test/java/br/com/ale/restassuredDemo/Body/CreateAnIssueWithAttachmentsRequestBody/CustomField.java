package br.com.ale.restassuredDemo.Body.CreateAnIssueWithAttachmentsRequestBody;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomField {
    @JsonProperty("field")
    private Field field;
    @JsonProperty("value")
    private String value;

    public Field getField() { return field; }
    public void setField(Field value) { this.field = value; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}
