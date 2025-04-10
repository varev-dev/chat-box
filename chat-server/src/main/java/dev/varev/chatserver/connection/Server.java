package dev.varev.chatserver.connection;

import dev.varev.chatshared.PropertiesLoader;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@NoArgsConstructor
public class Server implements Runnable {
    private State state;

    public enum State {
        ON,
        OFF
    }

    public void stop() {
        this.state = State.OFF;

        try {
            // need to flush runner in order to stop server
            Socket socket = new Socket(PropertiesLoader.getServerHost(), PropertiesLoader.getServerPort());
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
        try (ServerSocket serverSocket = new ServerSocket(PropertiesLoader.getServerPort())) {
            this.state = State.ON;
            System.out.println("Server listening on port " + PropertiesLoader.getServerPort());

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
