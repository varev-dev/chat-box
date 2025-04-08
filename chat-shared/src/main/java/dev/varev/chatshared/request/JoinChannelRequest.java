package dev.varev.chatshared.request;

import dev.varev.chatshared.dto.ChannelDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JoinChannelRequest implements Request {
    private final ChannelDTO channel;

    @Override
    public RequestType getType() {
        return RequestType.JOIN_CHANNEL;
    }
}
