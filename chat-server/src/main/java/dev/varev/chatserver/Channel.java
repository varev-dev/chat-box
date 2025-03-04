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

    public void broadcastAdmin(String message) {
        broadcast(message, null);
    }

    public void broadcastJoin(ClientHandler sender) {
        String message = sender.getUsername() + " joined the channel.";
        broadcast(message, sender);
    }

    public void broadcastDisconnect(ClientHandler sender) {
        String message = sender.getUsername() + " disconnected from this channel.";
        broadcast(message, sender);
    }

    public void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender)
                client.sendMessage(
                    (message.startsWith(Server.BROADCAST_PREFIX) ? "" : "[" + name + "]" + " ") + message);
        }
    }

    public void close() {
        for (ClientHandler client : clients)
            client.closeConnection();
    }

    public void addClient(ClientHandler client) {
        broadcastJoin(client);
        clients.add(client);
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public String getName() {
        return name;
    }
}
