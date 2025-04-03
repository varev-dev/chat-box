package dev.varev.chatserver.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    public static final String BROADCAST_PREFIX = "[SERVER]";
    private final int port;
    private State state;

    public enum State {
        ON,
        OFF
    }

    public Server(int port) {
        this.port = port;
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

    private void setupClientAndConnect(Socket socket) throws IOException {
        ClientHandler ch = new ClientHandler(socket, ConnectionManager.getInstance(), null);

        var t = new Thread(ch);
        t.start();
    }

    private void shutdown() {
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
}
