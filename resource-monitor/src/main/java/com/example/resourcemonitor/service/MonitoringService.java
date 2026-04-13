package com.example.resourcemonitor.service;

import com.example.resourcemonitor.model.AlertEvent;
import com.example.resourcemonitor.model.DiskInfo;
import com.example.resourcemonitor.model.DiskSetting;
import com.example.resourcemonitor.model.MemoryInfo;
import com.example.resourcemonitor.model.MonitoringSettings;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MonitoringService {

    private final DiskMonitorService diskMonitorService;
    private final MemoryMonitorService memoryMonitorService;
    private final LogService logService;
    private final NotificationService notificationService;

    public MonitoringService() {
        this.diskMonitorService = new DiskMonitorService();
        this.memoryMonitorService = new MemoryMonitorService();
        this.logService = new LogService();
        this.notificationService = new NotificationService();
    }

    public void checkResources(MonitoringSettings settings) {
        StringBuilder logText = new StringBuilder();
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        logText.append("[")
                .append(time)
                .append("] INFO === Resource Check ===")
                .append(System.lineSeparator());

        checkDisks(settings, logText);
        checkMemory(settings, logText);

        String finalLogText = logText.toString().trim();

        System.out.println(finalLogText);
        logService.log(finalLogText);
    }

    private void checkDisks(MonitoringSettings settings, StringBuilder logText) {
        List<DiskInfo> disks = diskMonitorService.getAllDisks();

        for (DiskInfo disk : disks) {
            DiskSetting diskSetting = findDiskSetting(settings, disk.getDiskName());

            if (diskSetting == null) {
                continue;
            }

            boolean percentExceeded = disk.getUsedPercent() >= diskSetting.getPercentLimit();
            boolean absoluteExceeded = disk.getFreeSpaceGb() <= diskSetting.getAbsoluteLimitGb();

            logText.append("Disk ")
                    .append(clearDiskName(disk.getDiskName()))
                    .append(": ")
                    .append(String.format("%.1f", disk.getUsedPercent()))
                    .append("% | free: ")
                    .append(String.format("%.1f", disk.getFreeSpaceGb()))
                    .append(" GB");

            if (percentExceeded || absoluteExceeded) {
                logText.append(" | Превышен порог диска");

                String alertMessage = "Превышен порог диска: " + disk.getDiskName();

                AlertEvent event = new AlertEvent(
                        "DISK",
                        disk.getDiskName(),
                        alertMessage,
                        LocalDateTime.now()
                );

                notificationService.notify(event);
            }

            logText.append(System.lineSeparator());
        }
    }

    private void checkMemory(MonitoringSettings settings, StringBuilder logText) {
        MemoryInfo memoryInfo = memoryMonitorService.getMemoryInfo();

        boolean percentExceeded = memoryInfo.getUsedPercent() >= settings.getMemoryPercentLimit();
        boolean absoluteExceeded = memoryInfo.getFreeMemoryGb() <= settings.getMemoryAbsoluteLimit();

        logText.append("Memory: ")
                .append(String.format("%.1f", memoryInfo.getUsedPercent()))
                .append("% | free: ")
                .append(String.format("%.1f", memoryInfo.getFreeMemoryGb()))
                .append(" GB");

        if (percentExceeded || absoluteExceeded) {
            logText.append(" | Превышен порог памяти");

            String alertMessage = "Превышен порог памяти";

            AlertEvent event = new AlertEvent(
                    "MEMORY",
                    "RAM",
                    alertMessage,
                    LocalDateTime.now()
            );

            notificationService.notify(event);
        }

        logText.append(System.lineSeparator());
    }

    private DiskSetting findDiskSetting(MonitoringSettings settings, String diskName) {
        for (DiskSetting diskSetting : settings.getDiskSettings()) {
            if (diskSetting.getDiskName().equals(diskName)) {
                return diskSetting;
            }
        }
        return null;
    }

    private String clearDiskName(String diskName) {
        return diskName.replace("\\", "");
    }
}