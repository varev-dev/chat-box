package dev.varev.chatclient;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        System.out.println("Client started, connected to: " + Client.DEFAULT_HOST + ":" + Client.DEFAULT_PORT);
        client.run();
    }
}
