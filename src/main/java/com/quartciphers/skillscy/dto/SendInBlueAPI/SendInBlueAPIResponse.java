package com.quartciphers.skillscy.dto.SendInBlueAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendInBlueAPIResponse {

    private String messageID;

    public String getMessageID() {
        return messageID;
    }

    @JsonProperty("messageId")
    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }
}
