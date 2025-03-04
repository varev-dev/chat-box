package dev.varev.chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import org.jetbrains.annotations.NotNull;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    private final Channel channel;
    private final String username;

    public ClientHandler(@NotNull Socket socket, Channel channel, String username) {
        this.socket = socket;

        if (socket.isClosed())
            throw new IllegalArgumentException("Socket is closed");

        this.channel = channel;
        this.username = username;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    private void disconnectFromChannel() {
        System.out.println(username + " disconnected from [" + channel.getName() + "]");
        channel.broadcastDisconnect(this);
        channel.removeClient(this);
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
            channel.addClient(this);

            String message;

            while (true) {
                try {
                    message = in.readLine();
                } catch (SocketException e) {
                    break;
                }

                if (message.compareTo("exit") == 0)
                    break;

                channel.broadcast(username + ": " + message, this);
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

    public String getUsername() {
        return username;
    }
}
