package dev.varev.chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    private final Channel channel;
    private String username;

    public ClientHandler(Socket socket, Channel channel) {
        this.socket = socket;
        this.channel = channel;

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

    @Override
    public void run() {
        try {
            username = "abc";
            channel.addClient(this);
            String message;
            while ((message = in.readLine()) != null) {
                channel.broadcast(username + ": " + message, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            channel.removeClient(this);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
