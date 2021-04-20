package com.quartciphers.skillscy.dto.SendInBlueAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ContentBody {

    private ContactInfo sender;
    private List<ContactInfo> receivers;
    private String subject;
    private String contentInHTML;

    @JsonProperty("sender")
    public ContactInfo getSender() {
        return sender;
    }

    public void setSender(ContactInfo sender) {
        this.sender = sender;
    }

    @JsonProperty("to")
    public List<ContactInfo> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<ContactInfo> receivers) {
        this.receivers = receivers;
    }

    @JsonProperty("subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @JsonProperty("htmlContent")
    public String getContentInHTML() {
        return contentInHTML;
    }

    public void setContentInHTML(String contentInHTML) {
        this.contentInHTML = contentInHTML;
    }
}
