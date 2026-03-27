package com.example.resourcemonitor.model;

import java.time.LocalDateTime;

public class AlertEvent {

    private String type;
    private String sourceName;
    private String message;
    private LocalDateTime eventTime;

    public AlertEvent() {
    }

    public AlertEvent(String type, String sourceName, String message, LocalDateTime eventTime) {
        this.type = type;
        this.sourceName = sourceName;
        this.message = message;
        this.eventTime = eventTime;
    }

    public String getType() {
        return type;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }
}