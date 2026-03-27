package com.example.resourcemonitor.service;

import com.example.resourcemonitor.util.FilePathUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class LogService {

    public void log(String message) {
        String logFilePath = FilePathUtil.getTodayLogFilePath();

        try (PrintWriter writer = new PrintWriter(new FileWriter(logFilePath, true))) {
            writer.println(LocalDateTime.now() + " - " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}