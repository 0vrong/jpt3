package com.example.resourcemonitor.model;

public class MemoryInfo {

    private double totalMemoryGb;
    private double freeMemoryGb;
    private double usedMemoryGb;
    private double usedPercent;

    public MemoryInfo() {
    }

    public MemoryInfo(double totalMemoryGb, double freeMemoryGb,
                      double usedMemoryGb, double usedPercent) {
        this.totalMemoryGb = totalMemoryGb;
        this.freeMemoryGb = freeMemoryGb;
        this.usedMemoryGb = usedMemoryGb;
        this.usedPercent = usedPercent;
    }

    public double getTotalMemoryGb() {
        return totalMemoryGb;
    }

    public void setTotalMemoryGb(double totalMemoryGb) {
        this.totalMemoryGb = totalMemoryGb;
    }

    public double getFreeMemoryGb() {
        return freeMemoryGb;
    }

    public void setFreeMemoryGb(double freeMemoryGb) {
        this.freeMemoryGb = freeMemoryGb;
    }

    public double getUsedMemoryGb() {
        return usedMemoryGb;
    }

    public void setUsedMemoryGb(double usedMemoryGb) {
        this.usedMemoryGb = usedMemoryGb;
    }

    public double getUsedPercent() {
        return usedPercent;
    }

    public void setUsedPercent(double usedPercent) {
        this.usedPercent = usedPercent;
    }
}