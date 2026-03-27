package com.example.resourcemonitor.service;

import com.example.resourcemonitor.model.MonitoringSettings;
import com.example.resourcemonitor.util.TimeWindowUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulerService {

    private final MonitoringService monitoringService;
    private ScheduledExecutorService executorService;

    public SchedulerService() {
        this.monitoringService = new MonitoringService();
    }

    public void startMonitoring(MonitoringSettings settings) {
        stopMonitoring();

        executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate(() -> {
            try {
                if (TimeWindowUtil.isAllowedTimeNow()) {
                    monitoringService.checkResources(settings);
                } else {
                    System.out.println("Сейчас нерабочее время для мониторинга");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, settings.getCheckIntervalSeconds(), TimeUnit.SECONDS);
    }

    public void stopMonitoring() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }
}