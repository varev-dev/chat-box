package dev.varev.chatserver.message;

import dev.varev.chatserver.account.Account;
import dev.varev.chatserver.channel.Channel;

import java.util.List;

public class MessageController {
    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    public boolean postMessage(Account author, Channel channel, String message) {
        return service.sendMessage(author, channel, message);
    }

    public List<Message> getMessages(Account author, Channel channel) {
        return service.getMessagesBySenderAndReceiver(author, channel);
    }

    public List<Message> getMessagesFromChannel(Channel channel) {
        return service.getMessagesByReceiver(channel);
    }

    public List<Message> getMessagesBySender(Account author) {
        return service.getMessagesBySender(author);
    }
}
