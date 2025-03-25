package dev.varev.chatserver.message;

import dev.varev.chatserver.account.Account;
import dev.varev.chatserver.channel.Channel;

import java.time.Instant;
import java.util.UUID;

public class Message {
    private final UUID uuid;
    private final Account author;
    private final Channel channel;
    private final String content;
    private final Instant createdAt;

    public Message(Account author, Channel channel, String content) {
        this.uuid = UUID.randomUUID();
        this.author = author;
        this.channel = channel;
        this.content = content;
        this.createdAt = Instant.now();
    }

    protected UUID getUuid() {
        return uuid;
    }

    public Account getAuthor() {
        return author;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getContent() {
        return content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
