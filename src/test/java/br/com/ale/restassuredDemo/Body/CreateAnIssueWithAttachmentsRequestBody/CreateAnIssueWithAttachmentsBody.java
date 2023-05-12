package br.com.ale.restassuredDemo.Body.CreateAnIssueWithAttachmentsRequestBody;


import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateAnIssueWithAttachmentsBody {
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("description")
    private String description;
    @JsonProperty("project")
    private Project project;
    @JsonProperty("category")
    private Category category;
    @JsonProperty("custom_fields")
    private CustomField[] customFields;
    @JsonProperty("files")
    private File[] files;

    public String getSummary() { return summary; }
    public void setSummary(String value) { this.summary = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public Project getProject() { return project; }
    public void setProject(Project value) { this.project = value; }

    public Category getCategory() { return category; }
    public void setCategory(Category value) { this.category = value; }

    public CustomField[] getCustomFields() {
        return customFields;
    }
    public void setCustomFields(CustomField[] value) {  this.customFields = value;    }
    public File[] getFiles() { return files; }
    public void setFiles(File[] value) { this.files = value; }

}
