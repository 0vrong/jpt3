package com.example.resourcemonitor.service;

import com.example.resourcemonitor.model.AlertEvent;
import com.example.resourcemonitor.model.DiskInfo;
import com.example.resourcemonitor.model.DiskSetting;
import com.example.resourcemonitor.model.MemoryInfo;
import com.example.resourcemonitor.model.MonitoringSettings;

import java.time.LocalDateTime;
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
        logService.log("Запущена проверка ресурсов");
        checkDisks(settings);
        checkMemory(settings);
    }

    private void checkDisks(MonitoringSettings settings) {
        List<DiskInfo> disks = diskMonitorService.getAllDisks();

        for (DiskInfo disk : disks) {
            DiskSetting diskSetting = findDiskSetting(settings, disk.getDiskName());

            if (diskSetting == null) {
                continue;
            }

            boolean percentExceeded = disk.getUsedPercent() >= diskSetting.getPercentLimit();
            boolean absoluteExceeded = disk.getFreeSpaceGb() <= diskSetting.getAbsoluteLimitGb();

            String infoMessage = "Диск " + disk.getDiskName()
                    + ", использовано %: " + disk.getUsedPercent()
                    + ", свободно ГБ: " + disk.getFreeSpaceGb();

            System.out.println(infoMessage);
            logService.log(infoMessage);

            if (percentExceeded || absoluteExceeded) {
                String alertMessage = "ПРЕВЫШЕН ПОРОГ ПО ДИСКУ: " + disk.getDiskName();

                AlertEvent event = new AlertEvent(
                        "DISK",
                        disk.getDiskName(),
                        alertMessage,
                        LocalDateTime.now()
                );

                logService.log(alertMessage);
                notificationService.notify(event);
            }
        }
    }

    private DiskSetting findDiskSetting(MonitoringSettings settings, String diskName) {
        for (DiskSetting diskSetting : settings.getDiskSettings()) {
            if (diskSetting.getDiskName().equals(diskName)) {
                return diskSetting;
            }
        }
        return null;
    }

    private void checkMemory(MonitoringSettings settings) {
        MemoryInfo memoryInfo = memoryMonitorService.getMemoryInfo();

        boolean percentExceeded = memoryInfo.getUsedPercent() >= settings.getMemoryPercentLimit();
        boolean absoluteExceeded = memoryInfo.getFreeMemoryGb() <= settings.getMemoryAbsoluteLimit();

        String infoMessage = "Память, использовано %: " + memoryInfo.getUsedPercent()
                + ", свободно ГБ: " + memoryInfo.getFreeMemoryGb();

        System.out.println(infoMessage);
        logService.log(infoMessage);

        if (percentExceeded || absoluteExceeded) {
            String alertMessage = "ПРЕВЫШЕН ПОРОГ ПО ПАМЯТИ";

            AlertEvent event = new AlertEvent(
                    "MEMORY",
                    "RAM",
                    alertMessage,
                    LocalDateTime.now()
            );

            logService.log(alertMessage);
            notificationService.notify(event);
        }
    }
}