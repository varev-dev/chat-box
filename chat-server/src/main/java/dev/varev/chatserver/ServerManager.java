package dev.varev.chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
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
                this.server.stop();
                break;
            }
        }
    }
}
