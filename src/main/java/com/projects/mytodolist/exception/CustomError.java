package com.projects.mytodolist.exception;

import java.time.LocalDateTime;

public class CustomError {
    private final String userMessage;
    private final String devMessage;
    private final LocalDateTime TIMESTAMP = LocalDateTime.now();

    public CustomError(String userMessage, String devMessage) {
        this.devMessage = devMessage;
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public String getDevMessage() {
        return devMessage;
    }

    public LocalDateTime getTIMESTAMP() {
        return TIMESTAMP;
    }
}