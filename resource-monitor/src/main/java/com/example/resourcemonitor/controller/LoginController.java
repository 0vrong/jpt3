package com.example.resourcemonitor.controller;

import com.example.resourcemonitor.service.AuthService;
import com.example.resourcemonitor.service.SessionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;

public class LoginController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    private final AuthService authService = new AuthService();
    private final SessionService sessionService = new SessionService();

    @FXML
    private void handleLogin() {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login.isBlank() || password.isBlank()) {
            messageLabel.setText("Введите логин и пароль");
            return;
        }

        boolean success = authService.authenticate(login, password);

        if (success) {
            try {
                sessionService.saveSession(login);
                openSettingsWindow();
                closeLoginWindow();
            } catch (Exception e) {
                e.printStackTrace();
                messageLabel.setText("Ошибка открытия окна настроек");
            }
        } else {
            messageLabel.setText("Неверный логин или пароль");
        }
    }

    private void openSettingsWindow() throws Exception {
        URL fxmlUrl = getClass().getResource("/fxml/settings-view.fxml");

        if (fxmlUrl == null) {
            throw new RuntimeException("Файл settings-view.fxml не найден");
        }

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Scene scene = new Scene(loader.load(), 760, 720);

        Stage stage = new Stage();
        stage.setTitle("Resource Monitor - Настройки");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    private void closeLoginWindow() {
        Stage currentStage = (Stage) loginField.getScene().getWindow();
        currentStage.close();
    }
}