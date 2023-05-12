package br.com.ale.restassuredDemo.Body.UpdateAProjectRequestBody;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateAProjectPatchBody {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("enabled")
    private boolean enabled;
    @JsonProperty("inherit_global")
    private long inheritGlobal;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public boolean getEnabled() { return enabled; }
    public void setEnabled(boolean value) { this.enabled = value; }

    public long getInheritGlobal() { return inheritGlobal; }
    public void setInheritGlobal(long value) { this.inheritGlobal = value; }
}
