package dev.varev.chatserver.connection;

import dev.varev.chatserver.account.AccountController;
import dev.varev.chatserver.channel.ChannelController;
import dev.varev.chatserver.membership.MembershipController;
import dev.varev.chatserver.message.MessageController;
import dev.varev.chatshared.AuthenticationDTO;
import dev.varev.chatshared.Response;
import dev.varev.chatshared.request.Request;

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

    public Response dispatch(Request request) {
        return switch (request.getType()) {
            case REGISTER -> account.register((AuthenticationDTO) request);
            case AUTHENTICATION -> account.authenticate((AuthenticationDTO) request);
            case SEND_MESSAGE -> null;
            case FETCH_MESSAGES -> null;
            case JOIN_CHANNEL -> null;
            case LEAVE_CHANNEL -> null;
            case PUBLIC_CHANNELS -> null;
        };
    }
}
