package dev.varev.chatserver;

import java.util.Set;

public class Channel {
    private final String name;
    private Set<ClientHandler> clients;

    public Channel(String name) {
        this.name = name;
    }

    public void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            //if (client != sender)
                client.sendMessage("[" + name + "]" + " " + message);
        }
    }

    public void addClient(ClientHandler client) {
        clients.add(client);
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }
}
