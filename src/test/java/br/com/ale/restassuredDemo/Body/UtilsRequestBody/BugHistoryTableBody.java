package br.com.ale.restassuredDemo.Body.UtilsRequestBody;

import br.com.ale.restassuredDemo.utils.UtilsQuery;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BugHistoryTableBody {
    @JsonProperty("user_id")
    private int userID;
    @JsonProperty("bug_id")
    private int bugID;
    @JsonProperty("field_name")
    private String fieldName;
    @JsonProperty("old_value")
    private String oldValue;
    @JsonProperty("new_value")
    private String newValue;
    @JsonProperty("type")
    private int type;
    @JsonProperty("date_modified")
    private int data;


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getBugID() {
        return bugID;
    }

    public void setBugID(int bugID) {
        this.bugID = bugID;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getData() {
        return UtilsQuery.getDataEpochTime();
    }

    public void setData(int data) {
        this.data = data;
    }

}
