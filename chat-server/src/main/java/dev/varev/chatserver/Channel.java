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
        broadcast(Server.BROADCAST_PREFIX + " " + message, null);
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
        broadcastAdmin("Channel '" + name + "' is being closed.");
        for (ClientHandler client : clients)
            client.closeConnection();
    }

    public void addClient(ClientHandler client) {
        broadcastJoin(client);
        clients.add(client);
    }

    public boolean removeClientWithName(String name) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(name)) {
                client.sendMessage(Server.BROADCAST_PREFIX + " You have been removed.");
                removeClient(client);
                return true;
            }
        }
        return false;
    }

    public boolean hasUserWithName(String name) {
        return clients.stream().anyMatch(client -> client.getUsername().equals(name));
    }

    public void removeClient(ClientHandler client) {
        client.closeConnection();
        clients.remove(client);
    }

    public String getName() {
        return name;
    }
}
