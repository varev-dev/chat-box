package dev.varev.chatserver.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.varev.chatserver.message.Message;
import dev.varev.chatshared.MessageDTO;
import dev.varev.chatshared.request.Request;
import dev.varev.chatshared.request.SendMessageRequest;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final ConnectionManager connectionManager;
    private final RequestDispatcher requestDispatcher;

    public ClientHandler(Socket socket, ConnectionManager connectionManager, RequestDispatcher requestDispatcher) {
        this.socket = socket;
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.connectionManager = connectionManager;
        this.requestDispatcher = requestDispatcher;
    }

    public void send(MessageDTO request) {
        try {
            out.writeObject(request);
        } catch (IOException ignored) {}
    }

    @Override
    public void run() {
        connectionManager.addClientHandler("1", this);
        while (!Thread.interrupted()) {
            try {
                Object input = in.readObject();
                System.out.println(input);

                if (input instanceof Request request) {
                    if (request instanceof SendMessageRequest smr) {
                        System.out.println(smr.getMessage());
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("");
            }
        }
    }

    private void closeConnection() {
        try {
            socket.close();
            // remove from connection manager if authenticated?
        } catch (IOException e) {
            // TODO: error logging
            e.printStackTrace();
        }
    }
}
