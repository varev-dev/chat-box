package dev.varev.chatserver.channel;

import dev.varev.chatserver.account.Account;
import dev.varev.chatserver.message.Message;
import dev.varev.chatserver.message.MessageService;

import java.util.Optional;

public class ChannelService {
    private final ChannelRepository repo;
    private final MessageService messageService;

    public ChannelService(ChannelRepository repo, MessageService messageService) {
        this.repo = repo;
        this.messageService = messageService;
    }

    public Optional<Channel> getChannelWithName(String name) {
        return repo.getChannelWithName(name);
    }

    public void broadcast(Message message) {
        // TODO: broadcast to connected clients (ConnectionManager?)
    }

    public void broadcastAdmin(Message message) {
        // TODO: broadcast as admin with prefix indicating server level author
    }

    public void broadcastJoin(Account account) {
        // TODO: broadcast user joined channel
    }

    public void broadcastExit(Account account) {
        // TODO: broadcast user left channel
    }
}
