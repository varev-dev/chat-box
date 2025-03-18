package dev.varev.chatserver.channel;

import java.time.Instant;
import java.util.UUID;

public class Channel {
    private final UUID id;
    private final String name;
    private final Instant createdAt;

    public Channel(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.createdAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
    /*public static boolean validateName(String name) {
        return name.matches("^[a-zA-Z0-9@#$&!?_-]{3,64}$");
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
        accounts.add(client.getAccount());
    }

    public boolean removeClientWithName(String name) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(name)) {
                client.sendMessage(Server.BROADCAST_PREFIX + " You have been removed from the channel.");
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

    public Set<ClientHandler> getClients() {
        return clients;
    }*/
}
