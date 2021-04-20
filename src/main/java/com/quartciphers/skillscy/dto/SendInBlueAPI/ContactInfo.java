package com.quartciphers.skillscy.dto.SendInBlueAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactInfo {

    private String eMailID;
    private String name;

    @JsonProperty("email")
    public String getEMailID() {
        return eMailID;
    }

    public void setEMailID(String eMailID) {
        this.eMailID = eMailID;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
