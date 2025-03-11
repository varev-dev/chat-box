package dev.varev.chatserver.membership;

import dev.varev.chatserver.account.Account;
import dev.varev.chatserver.channel.Channel;

import java.time.LocalDateTime;

public class Membership {
    private Account account;
    private Channel channel;

    private LocalDateTime joinedAt;
    private LocalDateTime leftAt;

    public Account getAccount() {
        return account;
    }

    public Channel getChannel() {
        return channel;
    }
}
