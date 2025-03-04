package dev.varev.chatserver;

public class Main {
    public static void main(String[] args) {
        System.out.println("Server starting...");
        Server server = new Server(8088);
        ServerManager manager = new ServerManager(server);
        new Thread(server).start();
        manager.run();
    }
}
