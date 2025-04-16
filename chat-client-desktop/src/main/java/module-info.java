module dev.varev.chatclientdesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.httpserver;
    requires dev.varev.chatshared;
    requires static lombok;

    opens dev.varev.chatclientdesktop to javafx.fxml;
    exports dev.varev.chatclientdesktop;
    exports dev.varev.chatclientdesktop.controller;
    opens dev.varev.chatclientdesktop.controller to javafx.fxml;
}