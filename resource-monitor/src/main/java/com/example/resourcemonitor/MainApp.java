package com.example.resourcemonitor;

import com.example.resourcemonitor.service.SessionService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;

public class MainApp extends Application {

    private static Stage primaryStage;
    private final SessionService sessionService = new SessionService();

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        Platform.setImplicitExit(false);

        if (sessionService.isLoggedIn()) {
            showSessionChoice();
        } else {
            openLoginWindow();
        }
    }

    private void showSessionChoice() throws Exception {
        String savedLogin = sessionService.getSavedLogin();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Профиль уже активен");
        alert.setHeaderText("Пользователь уже входил раньше");
        alert.setContentText("Логин: " + savedLogin + "\nПродолжить в этом профиле или выйти из него?");

        ButtonType continueButton = new ButtonType("Продолжить");
        ButtonType logoutButton = new ButtonType("Выйти из профиля");

        alert.getButtonTypes().setAll(continueButton, logoutButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == continueButton) {
            openSettingsWindow();
        } else {
            sessionService.clearSession();
            openLoginWindow();
        }
    }

    public static void openSettingsWindow() {
        Platform.runLater(() -> {
            try {
                URL fxmlUrl = MainApp.class.getResource("/fxml/settings-view.fxml");

                if (fxmlUrl == null) {
                    throw new RuntimeException("Файл settings-view.fxml не найден");
                }

                FXMLLoader loader = new FXMLLoader(fxmlUrl);
                Scene scene = new Scene(loader.load(), 760, 720);

                primaryStage.setTitle("Resource Monitor - Настройки");
                primaryStage.setScene(scene);
                primaryStage.setResizable(true);
                primaryStage.show();
                primaryStage.setIconified(false);
                primaryStage.toFront();
                primaryStage.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void openLoginWindow() {
        Platform.runLater(() -> {
            try {
                URL fxmlUrl = MainApp.class.getResource("/fxml/login-view.fxml");

                if (fxmlUrl == null) {
                    throw new RuntimeException("Файл login-view.fxml не найден");
                }

                FXMLLoader loader = new FXMLLoader(fxmlUrl);
                Scene scene = new Scene(loader.load(), 420, 260);

                primaryStage.setTitle("Resource Monitor - Вход");
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                primaryStage.show();
                primaryStage.setIconified(false);
                primaryStage.toFront();
                primaryStage.requestFocus();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}