package br.com.ale.restassuredDemo.Body.CreateAnIssueCopyRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateAnIssueCopyBody {
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("description")
    private String description;
    @JsonProperty("category")
    private Category category;
    @JsonProperty("project")
    private Category project;
    @JsonProperty("tags")
    private Category[] tags;

    public String getSummary() { return summary; }
    public void setSummary(String value) { this.summary = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public Category getCategory() { return category; }
    public void setCategory(Category value) { this.category = value; }

    public Category getProject() { return project; }
    public void setProject(Category value) { this.project = value; }

    public Category[] getTags() { return tags; }
    public void setTags(Category[] value) { this.tags = value; }
}
