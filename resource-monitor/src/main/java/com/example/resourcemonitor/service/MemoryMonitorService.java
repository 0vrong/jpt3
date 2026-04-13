package com.example.resourcemonitor.service;

import com.example.resourcemonitor.model.MemoryInfo;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

public class MemoryMonitorService {

    public MemoryInfo getMemoryInfo() {
        OperatingSystemMXBean osBean =
                (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        long totalBytes = osBean.getTotalMemorySize();
        long freeBytes = osBean.getFreeMemorySize();
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