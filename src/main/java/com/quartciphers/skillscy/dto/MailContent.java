package com.quartciphers.skillscy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MailContent {

    private ClientInfo clientInfo;
    private String name;
    private String eMailID;
    private String phoneNumber;
    private String message;

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    @JsonProperty("ClientInfo")
    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    public String geteMailID() {
        return eMailID;
    }

    @JsonProperty("EMailID")
    public void seteMailID(String eMailID) {
        this.eMailID = eMailID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("MobileNumber")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    @JsonProperty("Query")
    public void setMessage(String message) {
        this.message = message;
    }
}
