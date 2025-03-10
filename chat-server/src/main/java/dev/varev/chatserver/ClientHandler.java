package dev.varev.chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.time.Duration;
import java.util.Map;
import java.util.Set;

import org.jetbrains.annotations.NotNull;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    private Channel channel;
    private Account account;

    private Set<Account> accounts;
    private Map<String, Channel> channels;

    public ClientHandler(@NotNull Socket socket, Set<Account> accounts, Map<String, Channel> channels) {
        this.socket = socket;

        if (socket.isClosed())
            throw new IllegalArgumentException("Socket is closed");

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.channels = channels;
        this.accounts = accounts;
    }

    private Channel assignClientWithChannel(Map<String, Channel> channels) throws IOException {
        if (!channels.isEmpty()) {
            out.println("--- Available channels ---");
            for (String channel : channels.keySet().stream().sorted().toList())
                out.println(channel);
        }
        out.println("Select available channel or create new one (min. length 3 characters): ");

        String channelName = in.readLine().trim().toUpperCase();

        if (!Channel.validateName(channelName)) {
            out.println("Invalid channel name: " + channelName);
            return null;
        }

        Channel channel;
        channel = channels.computeIfAbsent(channelName, Channel::new);
        return channel;
    }

    private Account assignClientWithAccount(Set<Account> accounts) throws IOException {
        out.print("1. Login\n2. Register\nElse. Exit\nEnter option: ");
        out.flush();
        String option = in.readLine().toLowerCase();

        return switch (option) {
            case "1", "login" -> login(accounts);
            case "2", "register" -> register(accounts);
            default -> null;
        };
    }

    private Account register(Set<Account> accounts) throws IOException {
        out.print("Enter username: ");
        out.flush();
        String username = in.readLine();

        if (isUsernameOccupied(accounts, username)) {
            out.println("Account with given username already exists.");
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

    private Account login(Set<Account> accounts) throws IOException {
        Account account;
        out.print("Enter username: ");
        out.flush();
        String username = in.readLine();
        account = getAccountByUsername(accounts, username);

        if (account == null) {
            out.println("Account with given username was not found.");
            return null;
        }

        out.print("Enter password: ");
        out.flush();
        String password = in.readLine();

        return account.verify(password) ? account : null;
    }

    private boolean setup() throws IOException {
        this.account = assignClientWithAccount(accounts);
        if (this.account == null) {
            in.close();
            out.close();
            return false;
        }

        this.channel = assignClientWithChannel(channels);
        if (this.channel == null) {
            in.close();
            out.close();
            return false;
        }

        System.out.println(account.getUsername() + " connected to " + "[" + channel.getName() + "]" );
        return true;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    private void disconnectFromChannel() {
        System.out.println(account.getUsername() + " disconnected from [" + channel.getName() + "]");
        channel.broadcastDisconnect(this);
        channel.removeClient(this);
    }

    protected boolean isConnected() {
        return socket.isConnected();
    }

    protected void closeConnection() {
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            if (!setup())
                return;
            channel.addClient(this);

            String message;

            while (true) {
                try {
                    message = in.readLine();
                } catch (SocketException e) {
                    break;
                }

                if (message.equalsIgnoreCase("exit"))
                    break;

                channel.broadcast(account.getUsername()+ ": " + message, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (!socket.isClosed()) {
                disconnectFromChannel();
                closeConnection();
            }
        }
    }

    private boolean isUsernameOccupied(Set<Account> accounts, String username) {
        return getAccountByUsername(accounts, username) != null;
    }

    private Account getAccountByUsername(Set<Account> accounts, String username) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username))
                return account;
        }
        return null;
    }

    public String getUsername() {
        return account.getUsername();
    }
}
