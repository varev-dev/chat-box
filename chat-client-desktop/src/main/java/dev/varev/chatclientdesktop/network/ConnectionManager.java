package dev.varev.chatclientdesktop.network;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectionManager {
    private static final ConnectionManager INSTANCE = new ConnectionManager();
    private Connection connection;

    public static ConnectionManager getInstance() {
        return INSTANCE;
    }
}
