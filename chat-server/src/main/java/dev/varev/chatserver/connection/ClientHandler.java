package dev.varev.chatserver.connection;

import dev.varev.chatshared.dto.MessageDTO;
import dev.varev.chatshared.request.Request;

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
        while (!Thread.interrupted()) {
            try {
                Object input = in.readObject();

                if (input instanceof Request request) {
                    var response = requestDispatcher.dispatch(request);
                    // todo: handle response
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        closeConnection();
    }

    private void closeConnection() {
        try {
            connectionManager.removeClientHandler(this);
            socket.close();
        } catch (IOException e) {
            // TODO: error logging
            e.printStackTrace();
        }
    }
}
