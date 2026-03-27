package com.example.resourcemonitor.service;

import com.example.resourcemonitor.MainApp;
import javafx.application.Platform;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;

public class TrayService {

    private static TrayIcon trayIcon;
    private static boolean initialized = false;

    public void initTray() {
        if (initialized) {
            return;
        }

        if (!SystemTray.isSupported()) {
            System.out.println("Системный трей не поддерживается");
            return;
        }

        try {
            SystemTray tray = SystemTray.getSystemTray();

            PopupMenu popupMenu = new PopupMenu();

            MenuItem openItem = new MenuItem("Открыть настройки");
            MenuItem exitItem = new MenuItem("Выход");

            openItem.addActionListener(e -> openSettings());
            exitItem.addActionListener(e -> {
                tray.remove(trayIcon);
                Platform.exit();
                System.exit(0);
            });

            popupMenu.add(openItem);
            popupMenu.add(exitItem);

            Image image = createTrayImage();

            trayIcon = new TrayIcon(image, "Resource Monitor", popupMenu);
            trayIcon.setImageAutoSize(true);

            trayIcon.addActionListener(e -> openSettings());

            tray.add(trayIcon);
            initialized = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openSettings() {
        Platform.runLater(() -> {
            try {
                MainApp.openSettingsWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private Image createTrayImage() {
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 16, 16);
        g.dispose();
        return image;
    }
}