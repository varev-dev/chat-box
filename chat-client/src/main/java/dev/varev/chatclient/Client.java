package dev.varev.chatclient;

import java.io.*;
import java.net.Socket;

public class Client {
    final public static String DEFAULT_HOST = "127.0.0.1";
    final public static int DEFAULT_PORT = 8088;

    private final Socket socket;
    private BufferedReader reader;
    private PrintWriter out;
    private BufferedReader serverReader;
    private PrintWriter serverWriter;

    public Client() {
        try {
            this.socket = new Socket(DEFAULT_HOST, DEFAULT_PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            if (socket.isConnected()) {
                this.reader = new BufferedReader(new InputStreamReader(System.in));
                this.serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.out = new PrintWriter(System.out, true);
                this.serverWriter = new PrintWriter(socket.getOutputStream(), true);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        Thread receiveThread = new Thread(() -> {
           try {
               String received;
               while ((received = serverReader.readLine()) != null) {
                   out.println(received);
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
        });

        receiveThread.start();

        String message = "";

        while (message.compareTo("exit") != 0) {
            try {
                message = reader.readLine();
                serverWriter.println(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            this.reader.close();
            this.serverReader.close();
            this.out.close();
            this.serverWriter.close();
            this.socket.close();
            receiveThread.interrupt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
