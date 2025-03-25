package dev.varev.chatserver.connection;

import dev.varev.chatserver.account.AccountController;
import dev.varev.chatserver.channel.ChannelController;
import dev.varev.chatserver.membership.MembershipController;
import dev.varev.chatserver.message.MessageController;

public class RequestDispatcher {
    private final AccountController account;
    private final ChannelController channel;
    private final MembershipController membership;
    private final MessageController message;

    public RequestDispatcher(AccountController account, ChannelController channel,
                             MembershipController membership, MessageController message) {
        this.account = account;
        this.channel = channel;
        this.membership = membership;
        this.message = message;
    }
}
