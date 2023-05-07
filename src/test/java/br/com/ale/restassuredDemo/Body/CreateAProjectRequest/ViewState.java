package br.com.ale.restassuredDemo.Body.CreateAProjectRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ViewState {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("label")
    private String label;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getLabel() { return label; }
    public void setLabel(String value) { this.label = value; }
}
