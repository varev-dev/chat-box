package dev.varev.chatserver.message;

import dev.varev.chatserver.account.Account;
import dev.varev.chatserver.channel.Channel;

import java.util.*;

public class MessageRepository {
    private final Map<UUID, Message> messages;

    public MessageRepository() {
        this.messages = new HashMap<>();
    }

    public MessageRepository(Map<UUID, Message> messages) {
        this.messages = messages;
    }

    protected Optional<Message> getMessageByUUID(UUID uuid) {
        return Optional.ofNullable(messages.get(uuid));
    }

    protected List<Message> getMessagesBySender(Account sender) {
        return messages.values().stream()
                .filter(message -> message.getAuthor() == sender)
                .sorted(Comparator.comparing(Message::getCreatedAt))
                .toList();
    }

    protected List<Message> getMessagesByReceiver(Channel receiver) {
        return messages.values().stream()
                .filter(message -> message.getChannel() == receiver)
                .sorted(Comparator.comparing(Message::getCreatedAt))
                .toList();
    }

    protected boolean addMessage(UUID uuid, Message message) {
        return messages.put(uuid, message) != null;
    }

    protected boolean removeMessage(UUID uuid) {
        return messages.remove(uuid) != null;
    }
}
