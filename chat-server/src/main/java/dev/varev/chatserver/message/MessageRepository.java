package dev.varev.chatserver.message;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MessageRepository {
    private final Map<UUID, Message> messages;

    public MessageRepository() {
        this.messages = new HashMap<>();
    }

    public MessageRepository(Map<UUID, Message> messages) {
        this.messages = messages;
    }
}
