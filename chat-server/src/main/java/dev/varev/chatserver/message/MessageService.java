package dev.varev.chatserver.message;

import dev.varev.chatserver.account.Account;
import dev.varev.chatserver.channel.Channel;

import java.util.List;
import java.util.Optional;

public class MessageService {
    private final MessageRepository repo;

    public MessageService(MessageRepository repo) {
        this.repo = repo;
    }

    public boolean sendMessage(Account account, Channel channel, String content) {
        var message = createMessage(account, channel, content);

        return message.filter(value -> repo.addMessage(value.getUuid(), value)).isPresent();
    }

    // TODO: pagination, receive up to X messages per request
    public List<Message> getMessagesByReceiver(Channel channel) {
        return repo.getMessagesByReceiver(channel);
    }

    public List<Message> getMessagesBySender(Account author) {
        return repo.getMessagesBySender(author);
    }

    public List<Message> getMessagesBySenderAndReceiver(Account sender, Channel receiver) {
        return repo.getMessagesBySenderAndReceiver(sender, receiver);
    }

    // TODO: check if user is not blocked
    private Optional<Message> createMessage(Account author, Channel channel, String content) {
        var message = new Message(author, channel, content);
        return Optional.of(message);
    }
}
