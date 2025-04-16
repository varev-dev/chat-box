package dev.varev.chatclientdesktop.network;


import dev.varev.chatshared.response.Response;
import dev.varev.chatshared.request.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Connection(Socket socket) {
        this.socket = socket;
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void send(Request request) {
        try {
            out.writeObject(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized Response receive() {
        try {
            var input = in.readObject();

            if (input instanceof Response response)
                return response;
            throw new ClassNotFoundException();
        } catch (IOException e) {
            // todo log io
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            // todo log cnf
            throw new RuntimeException(e);
        }
    }
}
