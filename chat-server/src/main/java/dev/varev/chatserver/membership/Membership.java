package dev.varev.chatserver.membership;

import dev.varev.chatserver.account.Account;
import dev.varev.chatserver.channel.Channel;

import java.time.Instant;

public class Membership {
    private final Account account;
    private final Channel channel;
    private boolean blocked;

    private final Instant joinedAt;
    private Instant leftAt;

    public Membership(Account account, Channel channel) {
        this.account = account;
        this.channel = channel;
        this.blocked = false;
        this.joinedAt = Instant.now();
    }

    public Account getAccount() {
        return account;
    }

    public Channel getChannel() {
        return channel;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setLeftAt(Instant leftAt) {
        this.leftAt = leftAt;
    }

    public Instant getLeftAt() {
        return leftAt;
    }

    public boolean isActive() {
        return leftAt == null && !blocked;
    }
}
