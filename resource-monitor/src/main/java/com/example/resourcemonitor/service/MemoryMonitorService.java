package com.example.resourcemonitor.service;

import com.example.resourcemonitor.model.MemoryInfo;

public class MemoryMonitorService {

    public MemoryInfo getMemoryInfo() {
        Runtime runtime = Runtime.getRuntime();

        long totalBytes = runtime.totalMemory();
        long freeBytes = runtime.freeMemory();
        long usedBytes = totalBytes - freeBytes;

        double totalGb = bytesToGb(totalBytes);
        double freeGb = bytesToGb(freeBytes);
        double usedGb = bytesToGb(usedBytes);
        double usedPercent = (usedGb / totalGb) * 100.0;

        return new MemoryInfo(totalGb, freeGb, usedGb, usedPercent);
    }

    private double bytesToGb(long bytes) {
        return bytes / 1024.0 / 1024.0 / 1024.0;
    }
}