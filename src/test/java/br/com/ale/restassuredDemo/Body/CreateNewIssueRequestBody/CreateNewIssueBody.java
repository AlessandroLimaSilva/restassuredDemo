package br.com.ale.restassuredDemo.Body.CreateNewIssueRequestBody;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateNewIssueBody {
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("description")
    private String description;
    @JsonProperty("additional_information")
    private String additionalInformation;
    @JsonProperty("project")
    private Category project;
    @JsonProperty("category")
    private Category category;
    @JsonProperty("handler")
    private Handler handler;
    @JsonProperty("view_state")
    private Category viewState;
    @JsonProperty("priority")
    private Handler priority;
    @JsonProperty("severity")
    private Handler severity;
    @JsonProperty("reproducibility")
    private Handler reproducibility;
    @JsonProperty("sticky")
    private boolean sticky;
    @JsonProperty("custom_fields")
    private CustomField[] customFields;
    @JsonProperty("tags")
    private Handler[] tags;


    public String getSummary() {
        return summary;
    }

    public void setSummary(String value) {
        this.summary = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String value) {
        this.additionalInformation = value;
    }

    public Category getProject() {
        return project;
    }

    public void setProject(Category value) {
        this.project = value;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category value) {
        this.category = value;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler value) {
        this.handler = value;
    }

    public Category getViewState() {
        return viewState;
    }

    public void setViewState(Category value) {
        this.viewState = value;
    }

    public Handler getPriority() {
        return priority;
    }

    public void setPriority(Handler value) {
        this.priority = value;
    }

    public Handler getSeverity() {
        return severity;
    }

    public void setSeverity(Handler value) {
        this.severity = value;
    }

    public Handler getReproducibility() {
        return reproducibility;
    }

    public void setReproducibility(Handler value) {
        this.reproducibility = value;
    }

    public boolean getSticky() {
        return sticky;
    }

    public void setSticky(boolean value) {
        this.sticky = value;
    }

    public CustomField[] getCustomFields() {
        return customFields;
    }

    public void setCustomFields(CustomField[] value) {
        this.customFields = value;
    }

    public Handler[] getTags() {
        return tags;
    }

    public void setTags(Handler[] value) {
        this.tags = value;
    }
}
