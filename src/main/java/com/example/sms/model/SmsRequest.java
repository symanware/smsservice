package com.example.sms.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public class SmsRequest {

    @NotNull(message = "Phone number is required")
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+\\d{10,15}$",
            message = "Phone number must be in international format (e.g., +1234567890)"
    )
    private String destinationNumber;
    private String content;
    private String format;

    public SmsRequest(){}

    public SmsRequest(String destinationNumber, String content, String format) {
        this.destinationNumber = destinationNumber;
        this.content = content;
        this.format = format;
    }

    public String getDestinationNumber() {
        return destinationNumber;
    }

    public void setDestinationNumber(String destinationNumber) {
        this.destinationNumber = destinationNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
