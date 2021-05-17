package com.quartciphers.skillscy.dto.repository;

import com.google.cloud.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class FeedbackDB {

    private String senderID;
    private String receiverID;
    private String feedback;
    private Timestamp timeOfCreation;

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Timestamp getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(Timestamp timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public Map<String, Object> dbValue() {
        Map<String, Object> mapObject = new HashMap<>();
        mapObject.put("from_id", this.senderID);
        mapObject.put("to_id", this.receiverID);
        mapObject.put("message", this.feedback);
        mapObject.put("timestamp", this.timeOfCreation);

        return mapObject;
    }
}
