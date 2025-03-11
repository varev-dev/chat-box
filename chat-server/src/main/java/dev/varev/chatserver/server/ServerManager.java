package dev.varev.chatserver.server;

import dev.varev.chatserver.ClientHandler;
import dev.varev.chatserver.account.Account;
import dev.varev.chatserver.channel.Channel;

import java.util.Scanner;
import java.util.Set;

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
            Set<Account> accounts = server.getAccounts();
            boolean blocked = false;

            for (Account account : accounts) {
                if (account.getUsername().equals(name)) {
                    account.block(Account.DEFAULT_TIMEOUT);
                    blocked = true;
                }
            }

            var channels = server.getChannels();
            for (Channel channel : channels.values()) {
                var clientHandler = channel.getClients().stream()
                        .filter(client -> client.getUsername().equals(name)).findFirst();

                clientHandler.ifPresent(ClientHandler::closeConnection);
            }

            return blocked; // mb ret no user exception
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
                if (String.valueOf(t.code).equals(input) || t.toString().equalsIgnoreCase(input)) {
                    type = t;
                    break;
                }
            }
            if (type == null) {
                System.out.println("Invalid object type");
                return;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid object type");
            return;
        }

        System.out.println("Enter " + type + " name:");
        name = in.nextLine();

        System.out.println(type + " named " + name + (blockObject(type, name) ? " blocked." : " not found."));
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
