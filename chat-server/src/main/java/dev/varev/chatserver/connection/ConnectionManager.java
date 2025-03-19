package dev.varev.chatserver.connection;

import java.util.Set;

public class ConnectionManager {
    private final Set<ClientHandler> connectedClients;

    public ConnectionManager(Set<ClientHandler> connectedClients) {
        this.connectedClients = connectedClients;
    }

    public void addClientHandler(ClientHandler clientHandler) {
        connectedClients.add(clientHandler);
    }

    public void removeClientHandler(ClientHandler clientHandler) {
        connectedClients.remove(clientHandler);
    }

    public Set<ClientHandler> getConnectedClients() {
        return connectedClients;
    }
}
