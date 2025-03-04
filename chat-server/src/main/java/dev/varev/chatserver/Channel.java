package dev.varev.chatserver;

import java.util.HashSet;
import java.util.Set;

public class Channel {
    private final String name;
    private final Set<ClientHandler> clients;

    public Channel(String name) {
        this.name = name;
        this.clients = new HashSet<>();
    }

    public void broadcastDisconnect(ClientHandler sender) {
        String message = sender.getUsername() + " disconnected from [" + name + "].";
        broadcast(message, sender);
    }

    public void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender)
                client.sendMessage("[" + name + "]" + " " + message);
        }
    }

    public void addClient(ClientHandler client) {
        clients.add(client);
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public String getName() {
        return name;
    }
}
