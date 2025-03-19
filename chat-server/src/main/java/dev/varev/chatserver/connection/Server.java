package dev.varev.chatserver.server;

import dev.varev.chatserver.ClientHandler;
import dev.varev.chatserver.account.Account;
import dev.varev.chatserver.channel.Channel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server implements Runnable {
    public static final String BROADCAST_PREFIX = "[SERVER]";
    private final int port;
    private final Map<String, Channel> channels;
    private final Set<Account> accounts;
    private State state;

    public enum State {
        ON,
        OFF
    }

    public Server(int port) {
        this.port = port;
        channels = new ConcurrentHashMap<>();
        accounts = new HashSet<>();
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

    private void setupClientAndConnect(Socket socket) {
        ClientHandler clientHandler = new ClientHandler(socket, accounts, channels);

        var t = new Thread(clientHandler);
        t.start();
    }

    private void shutdown() {
        for (var channel : channels.values())
            channel.close();

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

                setupClientAndConnect(client);
            }
            shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public Map<String, Channel> getChannels() {
        return channels;
    }
}
