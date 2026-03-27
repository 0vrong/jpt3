package com.example.resourcemonitor.controller;

import com.example.resourcemonitor.model.DiskInfo;
import com.example.resourcemonitor.model.DiskSetting;
import com.example.resourcemonitor.model.MonitoringSettings;
import com.example.resourcemonitor.service.DiskMonitorService;
import com.example.resourcemonitor.service.MonitoringService;
import com.example.resourcemonitor.service.SchedulerService;
import com.example.resourcemonitor.service.SessionService;
import com.example.resourcemonitor.service.SettingsService;
import com.example.resourcemonitor.service.TrayService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.resourcemonitor.MainApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsController {

    @FXML
    private TextField intervalField;

    @FXML
    private TextField memoryPercentField;

    @FXML
    private TextField memoryAbsoluteField;

    @FXML
    private VBox disksContainer;

    @FXML
    private Label messageLabel;

    private final SettingsService settingsService = new SettingsService();
    private final MonitoringService monitoringService = new MonitoringService();
    private final SchedulerService schedulerService = new SchedulerService();
    private final TrayService trayService = new TrayService();
    private final DiskMonitorService diskMonitorService = new DiskMonitorService();
    private final SessionService sessionService = new SessionService();

    private final Map<String, TextField> diskPercentFields = new HashMap<>();
    private final Map<String, TextField> diskAbsoluteFields = new HashMap<>();

    @FXML
    public void initialize() {
        createDiskFields();
        loadSavedSettings();
    }

    private void createDiskFields() {
        List<DiskInfo> disks = diskMonitorService.getAllDisks();

        for (DiskInfo disk : disks) {
            Label diskLabel = new Label("Диск " + disk.getDiskName());

            Label percentLabel = new Label("Порог по проценту");
            TextField percentField = new TextField();
            percentField.setPromptText("Например: 80");

            Label absoluteLabel = new Label("Порог по свободному месту (ГБ)");
            TextField absoluteField = new TextField();
            absoluteField.setPromptText("Например: 10");

            VBox diskBox = new VBox(6);
            diskBox.getChildren().addAll(
                    diskLabel,
                    percentLabel,
                    percentField,
                    absoluteLabel,
                    absoluteField
            );

            disksContainer.getChildren().add(diskBox);

            diskPercentFields.put(disk.getDiskName(), percentField);
            diskAbsoluteFields.put(disk.getDiskName(), absoluteField);
        }
    }

    @FXML
    private void handleSaveAndStart() {
        try {
            MonitoringSettings settings = readSettingsFromFields();
            settingsService.saveSettings(settings);
            schedulerService.startMonitoring(settings);

            Stage stage = (Stage) messageLabel.getScene().getWindow();
            trayService.initTray();
            stage.hide();

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Ошибка запуска программы");
        }
    }

    @FXML
    private void handleCheckNow() {
        try {
            MonitoringSettings settings = readSettingsFromFields();
            monitoringService.checkResources(settings);
            messageLabel.setText("Проверка выполнена, смотри консоль и лог");
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Ошибка проверки");
        }
    }

    @FXML
    private void handleLogout() {
        try {
            sessionService.clearSession();
            schedulerService.stopMonitoring();

            Stage stage = (Stage) messageLabel.getScene().getWindow();
            stage.hide();

            MainApp.openLoginWindow();

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Ошибка выхода из профиля");
        }
    }

    private MonitoringSettings readSettingsFromFields() {
        MonitoringSettings settings = new MonitoringSettings();

        int interval = Integer.parseInt(intervalField.getText());
        if (interval <= 0) {
            throw new IllegalArgumentException("Интервал должен быть больше 0");
        }

        settings.setCheckIntervalSeconds(interval);
        settings.setMemoryPercentLimit(Double.parseDouble(memoryPercentField.getText()));
        settings.setMemoryAbsoluteLimit(Double.parseDouble(memoryAbsoluteField.getText()));

        List<DiskSetting> diskSettings = new ArrayList<>();

        for (String diskName : diskPercentFields.keySet()) {
            double percent = Double.parseDouble(diskPercentFields.get(diskName).getText());
            double absolute = Double.parseDouble(diskAbsoluteFields.get(diskName).getText());

            diskSettings.add(new DiskSetting(diskName, percent, absolute));
        }

        settings.setDiskSettings(diskSettings);
        return settings;
    }

    private void loadSavedSettings() {
        try {
            MonitoringSettings settings = settingsService.loadSettings();

            intervalField.setText(String.valueOf(settings.getCheckIntervalSeconds()));
            memoryPercentField.setText(String.valueOf(settings.getMemoryPercentLimit()));
            memoryAbsoluteField.setText(String.valueOf(settings.getMemoryAbsoluteLimit()));

            for (DiskSetting diskSetting : settings.getDiskSettings()) {
                TextField percentField = diskPercentFields.get(diskSetting.getDiskName());
                TextField absoluteField = diskAbsoluteFields.get(diskSetting.getDiskName());

                if (percentField != null) {
                    percentField.setText(String.valueOf(diskSetting.getPercentLimit()));
                }

                if (absoluteField != null) {
                    absoluteField.setText(String.valueOf(diskSetting.getAbsoluteLimitGb()));
                }
            }

        } catch (Exception e) {
            intervalField.setText("60");
            memoryPercentField.setText("85");
            memoryAbsoluteField.setText("2");

            for (String diskName : diskPercentFields.keySet()) {
                diskPercentFields.get(diskName).setText("80");
                diskAbsoluteFields.get(diskName).setText("10");
            }
        }
    }
}