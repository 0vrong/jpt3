package com.example.resourcemonitor.service;

import com.example.resourcemonitor.model.AlertEvent;

public class NotificationService {

    private final EmailStubService emailStubService;
    private final LogService logService;

    public NotificationService() {
        this.emailStubService = new EmailStubService();
        this.logService = new LogService();
    }

    public void notify(AlertEvent event) {
        logService.log("Сработало уведомление: " + event.getMessage());
        emailStubService.sendEmail(event);
    }
}