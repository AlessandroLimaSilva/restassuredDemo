package br.com.ale.restassuredDemo.Body.IssueCreateAnIssueMinimalRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateBugTextTableBody {
    @JsonProperty("description")
    private String description;
    @JsonProperty("steps_to_reproduce")
    private String stepsToReproduce;
    @JsonProperty("additional_information")
    private String additionalInformation;

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setStepsToReproduce(String stepsToReproduce){
        this.stepsToReproduce = stepsToReproduce;
    }

    public String getStepsToReproduce(){
        return stepsToReproduce;
    }

    public void setAdditionalInformation(String additionalInformation){
        this.additionalInformation = additionalInformation;
    }

    public String getAdditionalInformation(){
        return additionalInformation;
    }
}
