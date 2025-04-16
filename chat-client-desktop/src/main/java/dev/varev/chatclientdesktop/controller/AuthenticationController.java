package dev.varev.chatclientdesktop.controller;

import dev.varev.chatclientdesktop.network.ConnectionManager;
import dev.varev.chatshared.dto.AuthenticationDTO;
import dev.varev.chatshared.request.AuthenticationRequest;
import dev.varev.chatshared.response.FailedAuthResponse;
import dev.varev.chatshared.response.SuccessfulAuthResponse;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AuthenticationController {
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submit;
    @FXML
    private Hyperlink registerLink;

    @FXML
    public void handleLogin() {
        var authDTO = new AuthenticationDTO(usernameField.getText(), passwordField.getText());
        var req = new AuthenticationRequest(authDTO);
        ConnectionManager.getInstance().getConnection().send(req);
        var res = ConnectionManager.getInstance().getConnection().receive();

        if (res instanceof FailedAuthResponse failedAuthResponse) {
            // todo write into error label received message

        } else if (res instanceof SuccessfulAuthResponse successfulAuthResponse) {
            // todo change scene and load user details into context

        }
    }
}
