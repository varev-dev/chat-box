package dev.varev.chatserver;

import dev.varev.chatserver.connection.Server;
import dev.varev.chatserver.connection.ServerManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("Server starting...");
        var server = new Server();
        ServerManager manager = new ServerManager(server);
        new Thread(server).start();
        //manager.run();
    }
}
