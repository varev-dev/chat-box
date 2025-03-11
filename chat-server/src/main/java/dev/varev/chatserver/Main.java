package dev.varev.chatserver;

import dev.varev.chatserver.server.Server;
import dev.varev.chatserver.server.ServerManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("Server starting...");
        Server server = new Server(8088);
        ServerManager manager = new ServerManager(server);
        new Thread(server).start();
        manager.run();
    }
}
