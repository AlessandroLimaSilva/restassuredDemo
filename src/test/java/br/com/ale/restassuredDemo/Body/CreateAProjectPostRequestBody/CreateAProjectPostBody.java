package br.com.ale.restassuredDemo.Body.CreateAProjectPostRequestBody;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateAProjectPostBody {
    @JsonProperty("name")
    private String name;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("description")
    private String description;
    @JsonProperty("file_path")
    private String filePath;
    @JsonProperty("view_state")
    private Status viewState;
    @JsonProperty("enabled")
    private boolean enabled;

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public Status getStatus() { return status; }
    public void setStatus(Status value) { this.status = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String value) { this.filePath = value; }

    public Status getViewState() { return viewState; }
    public void setViewState(Status value) { this.viewState = value; }

    public boolean getEnabled() { return enabled; }
    public void setEnabled(boolean value) { this.enabled = value; }
}
