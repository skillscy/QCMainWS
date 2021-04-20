package com.quartciphers.skillscy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientInfo {

    private String name;
    private String mailAddress;
    private String mailSubject;

    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    @JsonProperty("MailAddress")
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    @JsonProperty("MailSubject")
    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }
}
