package com.example.resourcemonitor.service;

import com.example.resourcemonitor.model.DiskSetting;
import com.example.resourcemonitor.model.MonitoringSettings;
import com.example.resourcemonitor.util.FilePathUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class SettingsService {

    public void saveSettings(MonitoringSettings settings) throws IOException {
        Properties properties = new Properties();

        properties.setProperty("memory.percent.limit", String.valueOf(settings.getMemoryPercentLimit()));
        properties.setProperty("memory.absolute.limit", String.valueOf(settings.getMemoryAbsoluteLimit()));
        properties.setProperty("check.interval.seconds", String.valueOf(settings.getCheckIntervalSeconds()));

        for (DiskSetting diskSetting : settings.getDiskSettings()) {
            String diskKey = diskSetting.getDiskName().replace("\\", "_").replace(":", "_");

            properties.setProperty("disk." + diskKey + ".name", diskSetting.getDiskName());
            properties.setProperty("disk." + diskKey + ".percent", String.valueOf(diskSetting.getPercentLimit()));
            properties.setProperty("disk." + diskKey + ".absolute", String.valueOf(diskSetting.getAbsoluteLimitGb()));
        }

        try (FileOutputStream outputStream = new FileOutputStream(FilePathUtil.getSettingsFilePath())) {
            properties.store(outputStream, "Monitoring settings");
        }
    }

    public MonitoringSettings loadSettings() throws IOException {
        Properties properties = new Properties();
        MonitoringSettings settings = new MonitoringSettings();

        try (FileInputStream inputStream = new FileInputStream(FilePathUtil.getSettingsFilePath())) {
            properties.load(inputStream);

            settings.setMemoryPercentLimit(
                    Double.parseDouble(properties.getProperty("memory.percent.limit", "85"))
            );
            settings.setMemoryAbsoluteLimit(
                    Double.parseDouble(properties.getProperty("memory.absolute.limit", "2"))
            );
            settings.setCheckIntervalSeconds(
                    Integer.parseInt(properties.getProperty("check.interval.seconds", "60"))
            );

            ArrayList<DiskSetting> diskSettings = new ArrayList<>();

            for (String key : properties.stringPropertyNames()) {
                if (key.startsWith("disk.") && key.endsWith(".name")) {
                    String prefix = key.substring(0, key.length() - 5);

                    String diskName = properties.getProperty(prefix + ".name");
                    double percent = Double.parseDouble(properties.getProperty(prefix + ".percent", "80"));
                    double absolute = Double.parseDouble(properties.getProperty(prefix + ".absolute", "10"));

                    diskSettings.add(new DiskSetting(diskName, percent, absolute));
                }
            }

            settings.setDiskSettings(diskSettings);
        }

        return settings;
    }
}