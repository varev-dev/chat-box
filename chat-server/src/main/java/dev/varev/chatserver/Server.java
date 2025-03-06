package dev.varev.chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

    private Map<String, String> getNameAndChannel(Socket socket) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Map<String, String> userData = new HashMap<>();

        out.print("Enter username: ");
        out.flush();
        String userName = in.readLine();

        out.print("Enter channel name: ");
        out.flush();
        String channelName = in.readLine();

        userData.put("username", userName);
        userData.put("channel", channelName);

        return userData;
    }

    private boolean isUsernameOccupied(String username) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username))
                return true;
        }
        return false;
    }

    private Account getAccountByUsername(String username) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username))
                return account;
        }
        return null;
    }

    private Channel assignClientWithChannel(PrintWriter out, BufferedReader in) throws IOException {
        if (!channels.isEmpty()) {
            out.println("--- Available channels ---\n");
            for (String channel : channels.keySet().stream().sorted().toList())
                out.println(channel);
        }
        out.println("Select available channel to join or create new by using unoccupied name (min. length 3 characters): ");

        String channelName = in.readLine().trim().toUpperCase();

        if (channelName.length() < 3)
            return null;

        Channel channel;
        channel = channels.computeIfAbsent(channelName, Channel::new);
        return channel;
    }

    private Account assignClientWithAccount(PrintWriter out, BufferedReader in) throws IOException {
        out.print("1. Login\n2. Register\nElse. Exit\nEnter option: ");
        out.flush();
        String option = in.readLine().toLowerCase();

        return switch (option) {
            case "1", "login" -> login(out, in);
            case "2", "register" -> register(out, in);
            default -> null;
        };
    }

    private Account register(PrintWriter out, BufferedReader in) throws IOException {
        out.print("Enter username: ");
        out.flush();
        String username = in.readLine();

        if (isUsernameOccupied(username)) {
            out.print("Account with given username already exists.");
            return null;
        }

        out.print("Enter password (min. 3 characters): ");
        out.flush();
        String password = in.readLine();

        if (password.length() < 3) {
            out.print("Password must be at least 3 characters.");
            return null;
        }

        var account = new Account(username, password);
        accounts.add(account);
        return account;
    }

    private Account login(PrintWriter out, BufferedReader in) throws IOException {
        Account account;
        out.print("Enter username: ");
        out.flush();
        String username = in.readLine();
        account = getAccountByUsername(username);

        if (account == null) {
            out.println("Account with given username was not found.");
            return null;
        }

        out.print("Enter password: ");
        out.flush();
        String password = in.readLine();

        return account.verify(password) ? account : null;
    }

    private void connectUserToChannel(Socket socket, Account account, Channel channel) {
        ClientHandler clientHandler = new ClientHandler(socket, channel, account);

        System.out.println(account.getUsername() + " connected to " + "[" + channel.getName() + "]" );
        var t = new Thread(clientHandler);
        t.start();
    }

    private void shutdown() {
        for (var channel : channels.values())
            channel.close();

        System.out.println("Server stopping...");
    }

    private void handleConnection(Socket client) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            var account = assignClientWithAccount(out, in);
            if (account == null) {
                in.close();
                out.close();
            }

            var channel = assignClientWithChannel(out, in);
            if (channel == null) {
                in.close();
                out.close();
            }

            connectUserToChannel(client, account, channel);
        } catch (IOException ignored) {}
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

                handleConnection(client);
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
