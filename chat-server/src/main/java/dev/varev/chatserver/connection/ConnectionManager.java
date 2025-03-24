package dev.varev.chatserver.connection;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    private static final ConnectionManager INSTANCE = new ConnectionManager();
    private final Map<String, ClientHandler> activeClients = new ConcurrentHashMap<>();

    public static ConnectionManager getInstance() {
        return INSTANCE;
    }

    public void addClientHandler(String username, ClientHandler clientHandler) {
        activeClients.put(username, clientHandler);
    }

    public synchronized void removeClientHandler(String username) {
        activeClients.remove(username);
    }

    public Set<ClientHandler> getConnectedClients() {
        return new HashSet<>(this.activeClients.values());
    }
}
