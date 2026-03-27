package com.example.resourcemonitor.service;

import com.example.resourcemonitor.model.AlertEvent;

public class EmailStubService {

    public void sendEmail(AlertEvent event) {
        System.out.println("ЗАГЛУШКА EMAIL:");
        System.out.println("Тип: " + event.getType());
        System.out.println("Источник: " + event.getSourceName());
        System.out.println("Сообщение: " + event.getMessage());
        System.out.println("Время: " + event.getEventTime());
    }
}