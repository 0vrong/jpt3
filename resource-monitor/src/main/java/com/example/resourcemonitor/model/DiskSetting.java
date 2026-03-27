package com.example.resourcemonitor.model;

public class DiskSetting {

    private String diskName;
    private double percentLimit;
    private double absoluteLimitGb;

    public DiskSetting() {
    }

    public DiskSetting(String diskName, double percentLimit, double absoluteLimitGb) {
        this.diskName = diskName;
        this.percentLimit = percentLimit;
        this.absoluteLimitGb = absoluteLimitGb;
    }

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    public double getPercentLimit() {
        return percentLimit;
    }

    public void setPercentLimit(double percentLimit) {
        this.percentLimit = percentLimit;
    }

    public double getAbsoluteLimitGb() {
        return absoluteLimitGb;
    }

    public void setAbsoluteLimitGb(double absoluteLimitGb) {
        this.absoluteLimitGb = absoluteLimitGb;
    }
}