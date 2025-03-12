package dev.varev.chatserver.message;

import dev.varev.chatserver.channel.Channel;
import dev.varev.chatserver.membership.Membership;

import java.util.List;

public class MessageController {
    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    public boolean postMessage(Membership membership, String message) {
        return service.sendMessage(membership, message);
    }

    public List<Message> getMessagesFromChannel(Channel channel) {
        return service.receiveMessagesFromChannel(channel);
    }
}
