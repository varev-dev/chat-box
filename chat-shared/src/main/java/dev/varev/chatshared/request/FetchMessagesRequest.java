package dev.varev.chatshared.request;

import dev.varev.chatshared.ChannelDTO;

public class FetchMessagesRequest implements Request {
    private final ChannelDTO channel;

    public FetchMessagesRequest(ChannelDTO channel) {
        this.channel = channel;
    }

    @Override
    public RequestType getType() {
        return RequestType.FETCH_MESSAGES;
    }

    public ChannelDTO getChannel() {
        return channel;
    }
}
