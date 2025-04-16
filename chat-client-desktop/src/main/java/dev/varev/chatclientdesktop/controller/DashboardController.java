package dev.varev.chatclientdesktop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DashboardController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button logout;

    @FXML
    public void handleLogout() {
        // todo clear context
    }
}
