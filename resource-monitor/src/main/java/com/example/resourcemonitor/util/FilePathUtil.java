package com.example.resourcemonitor.util;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FilePathUtil {

    private FilePathUtil() {
    }

    public static String getAppDataFolderPath() {
        String userHome = System.getProperty("user.home");
        String folderPath = userHome + File.separator + "resource-monitor-data";

        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        return folderPath;
    }

    public static String getSettingsFilePath() {
        return getAppDataFolderPath() + File.separator + "settings.properties";
    }

    public static String getSessionFilePath() {
        return getAppDataFolderPath() + File.separator + "session.properties";
    }

    public static String getLogsFolderPath() {
        String logsFolder = getAppDataFolderPath() + File.separator + "logs";

        File folder = new File(logsFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        return logsFolder;
    }

    public static String getTodayLogFilePath() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return getLogsFolderPath() + File.separator + today + ".log";
    }
}