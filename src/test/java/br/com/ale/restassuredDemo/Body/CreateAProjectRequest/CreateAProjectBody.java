package br.com.ale.restassuredDemo.Body.CreateAProjectRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateAProjectBody {
    @JsonProperty("name")
    private String name;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("description")
    private String description;
    @JsonProperty("enabled")
    private boolean enabled;
    @JsonProperty("file_path")
    private String filePath;
    @JsonProperty("view_state")
    private ViewState viewState;

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public Status getStatus() { return status; }
    public void setStatus(Status value) { this.status = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public boolean getEnabled() { return enabled; }
    public void setEnabled(boolean value) { this.enabled = value; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String value) { this.filePath = value; }

    public ViewState getViewState() { return viewState; }
    public void setViewState(ViewState value) { this.viewState = value; }
}
