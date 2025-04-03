package dev.varev.chatclient;

import dev.varev.chatshared.MessageDTO;
import dev.varev.chatshared.request.SendMessageRequest;

import java.io.*;
import java.net.Socket;
import java.time.Instant;
import java.util.Scanner;

public class Client {
    final public static String DEFAULT_HOST = "127.0.0.1";
    final public static int DEFAULT_PORT = 8888;

    private final Socket socket;
    private final Listener listener;
    private final ObjectOutputStream out;

    public Client() {
        try {
            this.socket = new Socket(DEFAULT_HOST, DEFAULT_PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.listener = new Listener(new ObjectInputStream(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        //listener.start();
        String message;
        Scanner scanner = new Scanner(System.in);

        while (socket.isConnected()) {
            try {
                message = scanner.nextLine();
                if (message.compareTo("exit") == 0)
                    break;
                var messageDTO = new MessageDTO(Instant.now(), message);
                SendMessageRequest req = new SendMessageRequest(messageDTO);
                out.flush();
                out.writeObject(req);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            this.listener.interrupt();
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
