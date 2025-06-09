package com.example.sms.exception;

import java.util.Date;
import java.util.List;

public class ErrorMessage {

    private int statusCode;
    private Date timestamp;
    private List<String> messages;
    private String description;

    public ErrorMessage(int statusCode, Date timestamp, List<String> message, String description) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.description = description;
        this.messages = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
