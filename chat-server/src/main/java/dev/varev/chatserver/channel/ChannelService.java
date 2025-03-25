package dev.varev.chatserver.channel;

import dev.varev.chatserver.connection.ClientHandler;
import dev.varev.chatserver.connection.ConnectionManager;
import dev.varev.chatserver.message.Message;
import dev.varev.chatserver.message.MessageDTO;
import dev.varev.chatserver.message.MessageService;

import java.util.Optional;
import java.util.Set;

public class ChannelService {
    private final ChannelRepository repo;
    private final MessageService messageService;
    private final ConnectionManager connectionManager;

    public ChannelService(ChannelRepository repo, MessageService messageService, ConnectionManager connectionManager) {
        this.repo = repo;
        this.messageService = messageService;
        this.connectionManager = connectionManager;
    }

    public Optional<Channel> getChannelWithName(String name) {
        return repo.getChannelWithName(name);
    }

    public void broadcast(Message message, ChannelAction action) {
        // TODO: broadcast to connected clients (ConnectionManager?)
        Set<ClientHandler> connectedClients = connectionManager.getConnectedClients();

        MessageDTO messageDTO = switch (action) {
            case JOIN -> messageService.joinMessage(message.getAuthor());
            case LEAVE -> messageService.leaveMessage(message.getAuthor());
            case MESSAGE, ADMIN_MESSAGE -> messageService.mapToDTO(message);
        };

        connectedClients.forEach(e -> e.sendMessage(messageDTO));
    }
}
