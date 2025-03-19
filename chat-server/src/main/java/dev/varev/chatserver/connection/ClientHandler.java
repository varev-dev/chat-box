package dev.varev.chatserver.connection;

import dev.varev.chatserver.message.MessageDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    private final ConnectionManager connectionManager;

    public ClientHandler(Socket socket, ConnectionManager connectionManager) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.connectionManager = connectionManager;
    }

    public void sendMessage(MessageDTO message) {
        out.println(message);
    }

    @Override
    public void run() {

    }

    private void closeConnection() {
        try {
            socket.close();
            connectionManager.removeClientHandler(this);
        } catch (IOException e) {
            // TODO: error logging
            e.printStackTrace();
        }
    }
}
