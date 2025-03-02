package dev.varev.chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private final int port;
    private static Map<String, Channel> channels;

    public Server(int port) {
        this.port = port;
        channels = new ConcurrentHashMap<>();
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);
            while (true) {
                Socket client = serverSocket.accept();
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                out.println("Enter username: ");
                String userName = in.readLine();

                out.println("Enter channel name: ");
                String channelName = in.readLine();

                Channel channel = channels.computeIfAbsent(channelName, Channel::new);
                ClientHandler clientHandler = new ClientHandler(client, channel, userName);

                System.out.println("[" + userName + "]" + " connected to " + channelName);
                clientHandler.run();
            }
        } catch (IOException e) {
            System.out.println("Server stopping...");
        }
    }
}
