package com.example.resourcemonitor.model;

public class DiskInfo {

    private String diskName;
    private double totalSpaceGb;
    private double freeSpaceGb;
    private double usedSpaceGb;
    private double usedPercent;

    public DiskInfo() {
    }

    public DiskInfo(String diskName, double totalSpaceGb, double freeSpaceGb,
                    double usedSpaceGb, double usedPercent) {
        this.diskName = diskName;
        this.totalSpaceGb = totalSpaceGb;
        this.freeSpaceGb = freeSpaceGb;
        this.usedSpaceGb = usedSpaceGb;
        this.usedPercent = usedPercent;
    }

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    public double getTotalSpaceGb() {
        return totalSpaceGb;
    }

    public void setTotalSpaceGb(double totalSpaceGb) {
        this.totalSpaceGb = totalSpaceGb;
    }

    public double getFreeSpaceGb() {
        return freeSpaceGb;
    }

    public void setFreeSpaceGb(double freeSpaceGb) {
        this.freeSpaceGb = freeSpaceGb;
    }

    public double getUsedSpaceGb() {
        return usedSpaceGb;
    }

    public void setUsedSpaceGb(double usedSpaceGb) {
        this.usedSpaceGb = usedSpaceGb;
    }

    public double getUsedPercent() {
        return usedPercent;
    }

    public void setUsedPercent(double usedPercent) {
        this.usedPercent = usedPercent;
    }
}