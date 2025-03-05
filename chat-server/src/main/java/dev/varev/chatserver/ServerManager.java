package dev.varev.chatserver;

import java.util.Map;
import java.util.Scanner;

public class ServerManager {
    private final Server server;
    private final Scanner in;


    private enum Commands {
        EXIT("exit"),
        BLOCK("block"),;


        final String code;
        Commands(String code) {
            this.code = code;
        }
    }

    private enum ObjectType {
        USER(1),
        CHANNEL(2);

        final int code;
        ObjectType(int code) {
            this.code = code;
        }
    }

    public ServerManager(Server server) {
        this.server = server;
        this.in = new Scanner(System.in);
    }

    private boolean blockObject(ObjectType type, String name) {
        if (type == ObjectType.USER) {
            Map<String, Channel> channels = server.getChannels();

            for (Map.Entry<String, Channel> entry : channels.entrySet()) {
                if (entry.getValue().hasUserWithName(name))
                    return entry.getValue().removeClientWithName(name);
            }
            return false; // mb ret no user exception
        } else if (type == ObjectType.CHANNEL) {
            var channel = server.getChannels().values()
                    .stream().filter(ch -> ch.getName().equals(name)).findFirst();

            if (channel.isPresent()) {
                channel.get().close();
                return true;
            }
        }
        return false;
    }

    private void blockProc() {
        System.out.println("Enter type of object to block:\n1. User\n2. Channel");

        ObjectType type = null;
        String name;

        try {
            String input = in.nextLine();
            for (var t : ObjectType.values()) {
                if (String.valueOf(t.code).equals(input)) {
                    type = t;
                    break;
                }
            }
            if (type == null)
                return;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid type");
            return;
        }

        System.out.println("Enter " + type + " name:");
        name = in.nextLine();
        blockObject(type, name);
    }

    public void run() {
        String command;

        while (true) {
            command = in.nextLine();
            if (command.compareTo(Commands.EXIT.code) == 0) {
                synchronized (server) {
                    this.server.stop();
                }
                break;
            } else if (command.compareTo(Commands.BLOCK.code) == 0) {
                blockProc();
            }
        }
        in.close();

        System.out.println("Manager shutting down...");
    }
}
