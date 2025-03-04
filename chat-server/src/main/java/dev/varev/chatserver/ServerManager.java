package dev.varev.chatserver;

import java.util.Scanner;

public class ServerManager {
    private final Server server;
    private static final String EXIT_COMMAND = "exit";

    public ServerManager(Server server) {
        this.server = server;
    }

    public void run() {
        String command;
        Scanner in = new Scanner(System.in);

        while (true) {
            command = in.nextLine();
            if (command.compareTo(EXIT_COMMAND) == 0) {
                synchronized (server) {
                    this.server.stop();
                }
                break;
            }
        }
        in.close();

        synchronized (server) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
            System.out.println("Manager shutting down...");
        }
    }
}
