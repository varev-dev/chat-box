package dev.varev.chatserver.message;

import dev.varev.chatserver.account.Account;
import dev.varev.chatserver.channel.Channel;
import dev.varev.chatserver.membership.Membership;

import java.time.Instant;
import java.util.UUID;

public class Message {
    private final UUID uuid;
    private final Membership membership;
    private final String content;
    private final Instant createdAt;

    public Message(Membership membership, String content) {
        this.uuid = UUID.randomUUID();
        this.membership = membership;
        this.content = content;
        this.createdAt = Instant.now();
    }

    protected UUID getUuid() {
        return uuid;
    }

    public Account getAuthor() {
        return membership.getAccount();
    }

    public Channel getChannel() {
        return membership.getChannel();
    }

    public String getContent() {
        return content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
