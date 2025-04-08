package dev.varev.chatserver.connection;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    private static final ConnectionManager INSTANCE = new ConnectionManager();
    private final Map<ClientHandler, String> activeClients = new ConcurrentHashMap<>();

    public static ConnectionManager getInstance() {
        return INSTANCE;
    }

    public synchronized void addClientHandler(String username, ClientHandler clientHandler) {
        activeClients.put(clientHandler, username);
    }

    public synchronized void removeClientHandler(ClientHandler clientHandler) {
        activeClients.remove(clientHandler);
    }

    public synchronized Set<ClientHandler> getConnectedClients() {
        return new HashSet<>(this.activeClients.keySet());
    }
}
