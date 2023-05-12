package br.com.ale.restassuredDemo.Body.IssueCreateAnIssueMinimalRequestBody;

import com.fasterxml.jackson.annotation.JsonProperty;
import br.com.ale.restassuredDemo.Body.CreateAnIssueWithAttachmentsRequestBody.Project;
public class IssueCreateAnIssueMinimalBody {
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("description")
    private String description;
    @JsonProperty("category")
    private Category category;
    @JsonProperty("project")
    private Project project;

    public String getSummary() { return summary; }
    public void setSummary(String value) { this.summary = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public Category getCategory() { return category; }
    public void setCategory(Category value) { this.category = value; }

    public Project getProject() { return project; }
    public void setProject(Project value) { this.project = value; }
}
