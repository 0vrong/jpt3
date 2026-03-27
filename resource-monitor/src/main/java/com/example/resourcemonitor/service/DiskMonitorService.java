package com.example.resourcemonitor.service;

import com.example.resourcemonitor.model.DiskInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DiskMonitorService {

    public List<DiskInfo> getAllDisks() {
        List<DiskInfo> disks = new ArrayList<>();

        File[] roots = File.listRoots();

        if (roots == null) {
            return disks;
        }

        for (File root : roots) {
            long totalBytes = root.getTotalSpace();
            long freeBytes = root.getFreeSpace();

            if (totalBytes <= 0) {
                continue;
            }

            double totalGb = bytesToGb(totalBytes);
            double freeGb = bytesToGb(freeBytes);
            double usedGb = totalGb - freeGb;
            double usedPercent = (usedGb / totalGb) * 100.0;

            DiskInfo diskInfo = new DiskInfo(
                    root.getAbsolutePath(),
                    totalGb,
                    freeGb,
                    usedGb,
                    usedPercent
            );

            disks.add(diskInfo);
        }

        return disks;
    }

    private double bytesToGb(long bytes) {
        return bytes / 1024.0 / 1024.0 / 1024.0;
    }
}