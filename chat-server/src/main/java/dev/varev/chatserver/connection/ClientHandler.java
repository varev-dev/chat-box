package dev.varev.chatserver.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.varev.chatserver.account.AccountController;
import dev.varev.chatserver.channel.ChannelController;
import dev.varev.chatserver.membership.MembershipController;
import dev.varev.chatserver.message.MessageController;
import dev.varev.chatserver.message.MessageDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final ObjectMapper mapper;

    private final ConnectionManager connectionManager;
    private final AccountController accountController;
    private final ChannelController channelController;
    private final MembershipController membershipController;
    private final MessageController messageController;

    public ClientHandler(Socket socket, ObjectMapper mapper, ConnectionManager connectionManager,
                         AccountController accountController, ChannelController channelController,
                         MembershipController membershipController, MessageController messageController) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.mapper = mapper;
        this.connectionManager = connectionManager;
        this.accountController = accountController;
        this.channelController = channelController;
        this.membershipController = membershipController;
        this.messageController = messageController;
    }

    public void sendMessage(MessageDTO message) {
        out.println(message);
    }

    @Override
    public void run() {
        while (Thread.interrupted()) {
            continue;
        }
    }

    private void parseRequest() throws IOException {
        
    }

    private void closeConnection() {
        try {
            socket.close();
            connectionManager.removeClientHandler(this);
        } catch (IOException e) {
            // TODO: error logging
            e.printStackTrace();
        }
    }
}
