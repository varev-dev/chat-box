package dev.varev.chatserver.message;

import dev.varev.chatserver.channel.Channel;
import dev.varev.chatserver.membership.Membership;

import java.util.List;
import java.util.Optional;

public class MessageService {
    private final MessageRepository repo;

    public MessageService(MessageRepository repo) {
        this.repo = repo;
    }

    public boolean sendMessage(Membership membership, String content) {
        var message = createMessage(membership, content);

        return message.filter(value -> repo.addMessage(value.getUuid(), value))
                .isPresent();
    }

    // TODO: pagination, receive up to X messages per request
    public List<Message> receiveMessagesFromChannel(Channel channel) {
        return repo.getMessagesByReceiver(channel);
    }

    // TODO: check if user is not blocked
    private Optional<Message> createMessage(Membership membership, String content) {
        var message = new Message(membership, content);
        return Optional.of(message);
    }
}
