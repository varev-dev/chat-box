module dev.varev.chatclientdesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.httpserver;
    requires dev.varev.chatshared;

    opens dev.varev.chatclientdesktop to javafx.fxml;
    exports dev.varev.chatclientdesktop;
}