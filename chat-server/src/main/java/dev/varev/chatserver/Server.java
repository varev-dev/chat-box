package dev.varev.chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server implements Runnable {
    public static final String BROADCAST_PREFIX = "[SERVER]";
    private final int port;
    private final Map<String, Channel> channels;
    private State state;

    public enum State {
        ON,
        OFF
    }

    public Server(int port) {
        this.port = port;
        channels = new ConcurrentHashMap<>();
    }

    public void stop() {
        this.state = State.OFF;

        try {
            // need to flush runner in order to stop server
            Socket socket = new Socket("127.0.0.1", port);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> getNameAndChannel(Socket socket) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Map<String, String> userData = new HashMap<>();

        out.println("Enter username: ");
        String userName = in.readLine();

        out.println("Enter channel name: ");
        String channelName = in.readLine();

        userData.put("username", userName);
        userData.put("channel", channelName);

        return userData;
    }

    private void connectUserToChannel(Socket socket, Map<String, String> userData) {
        if (socket.isConnected()) {
            Channel channel = channels.computeIfAbsent(userData.get("channel"), Channel::new);
            String userName = userData.get("username");
            ClientHandler clientHandler = new ClientHandler(socket, channel, userName);

            System.out.println(userName + " connected to " + "[" + channel.getName() + "]" );
            var t = new Thread(clientHandler);
            t.start();
        }
    }

    private void shutdown() {
        for (var channel : channels.values()) {
            channel.broadcastAdmin(BROADCAST_PREFIX + " " + channel.getName() + " shutting down");
            channel.close();
        }

        System.out.println("Server stopping...");
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            this.state = State.ON;
            System.out.println("Server listening on port " + port);

            while (true) {
                Socket client = serverSocket.accept();
                if (this.state == State.OFF)
                    break;
                var userData = getNameAndChannel(client);
                connectUserToChannel(client, userData);
            }
            shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Channel> getChannels() {
        return channels;
    }
}
