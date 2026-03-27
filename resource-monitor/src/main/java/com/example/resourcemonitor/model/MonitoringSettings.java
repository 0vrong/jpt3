package com.example.resourcemonitor.model;

import java.util.ArrayList;
import java.util.List;

public class MonitoringSettings {

    private List<DiskSetting> diskSettings;
    private double memoryPercentLimit;
    private double memoryAbsoluteLimit;
    private int checkIntervalSeconds;

    public MonitoringSettings() {
        this.diskSettings = new ArrayList<>();
    }

    public List<DiskSetting> getDiskSettings() {
        return diskSettings;
    }

    public void setDiskSettings(List<DiskSetting> diskSettings) {
        this.diskSettings = diskSettings;
    }

    public double getMemoryPercentLimit() {
        return memoryPercentLimit;
    }

    public void setMemoryPercentLimit(double memoryPercentLimit) {
        this.memoryPercentLimit = memoryPercentLimit;
    }

    public double getMemoryAbsoluteLimit() {
        return memoryAbsoluteLimit;
    }

    public void setMemoryAbsoluteLimit(double memoryAbsoluteLimit) {
        this.memoryAbsoluteLimit = memoryAbsoluteLimit;
    }

    public int getCheckIntervalSeconds() {
        return checkIntervalSeconds;
    }

    public void setCheckIntervalSeconds(int checkIntervalSeconds) {
        this.checkIntervalSeconds = checkIntervalSeconds;
    }
}