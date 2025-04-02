package dev.varev.chatshared.request;

import dev.varev.chatshared.ChannelDTO;

public class JoinChannelRequest implements Request {
    private final ChannelDTO channel;

    public JoinChannelRequest(ChannelDTO channel) {
        this.channel = channel;
    }

    @Override
    public RequestType getType() {
        return RequestType.JOIN_CHANNEL;
    }

    public ChannelDTO getChannel() {
        return channel;
    }
}
