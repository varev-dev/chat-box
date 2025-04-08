package dev.varev.chatserver.connection;

import dev.varev.chatshared.dto.MessageDTO;
import dev.varev.chatshared.request.Request;
import dev.varev.chatshared.response.ExitResponse;
import dev.varev.chatshared.response.Response;

import java.io.*;
import java.net.Socket;
import java.util.stream.Stream;

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

    public void send(Response response) {
        try {
            out.writeObject(response);
        } catch (IOException e) {
            // todo: log
        }
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Object input = in.readObject();

                if (input instanceof Request request) {
                    var response = requestDispatcher.dispatch(request);
                    // todo: if response==null ret error BAD_REQUEST

                    send(response);

                    if (response instanceof ExitResponse)
                        break;
                    // todo: handle response
                }
            } catch (IOException | ClassNotFoundException e) {
                // todo: log forced disconnection? (sth else?)
                // todo: send error INTERNAL
                break;
            }
        }
        closeConnection();
    }

    private void closeConnection() {
        connectionManager.removeClientHandler(this);

        Stream.of(in, out).forEach(stream -> {
            try {
                if (stream != null)
                    stream.close();
            } catch (IOException ignored) {
                // TODO: log exception during in/out closing
            }
        });

        try {
            socket.close();
        } catch (IOException e) {
            // TODO: log
        }

    }
}
