package com.example.resourcemonitor.service;

import com.example.resourcemonitor.util.FilePathUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SessionService {

    private static final String SESSION_FILE = FilePathUtil.getSessionFilePath();

    public void saveSession(String login) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("logged.in", "true");
        properties.setProperty("login", login);

        try (FileOutputStream outputStream = new FileOutputStream(SESSION_FILE)) {
            properties.store(outputStream, "User session");
        }
    }

    public boolean isLoggedIn() {
        Properties properties = new Properties();

        try (FileInputStream inputStream = new FileInputStream(SESSION_FILE)) {
            properties.load(inputStream);
            return Boolean.parseBoolean(properties.getProperty("logged.in", "false"));
        } catch (IOException e) {
            return false;
        }
    }

    public String getSavedLogin() {
        Properties properties = new Properties();

        try (FileInputStream inputStream = new FileInputStream(SESSION_FILE)) {
            properties.load(inputStream);
            return properties.getProperty("login", "");
        } catch (IOException e) {
            return "";
        }
    }

    public void clearSession() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("logged.in", "false");
        properties.setProperty("login", "");

        try (FileOutputStream outputStream = new FileOutputStream(SESSION_FILE)) {
            properties.store(outputStream, "User session");
        }
    }
}