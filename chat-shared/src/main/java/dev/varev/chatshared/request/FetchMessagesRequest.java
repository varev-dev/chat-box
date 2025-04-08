package dev.varev.chatshared.request;

import dev.varev.chatshared.dto.ChannelDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FetchMessagesRequest implements Request {
    private final ChannelDTO channel;

    @Override
    public RequestType getType() {
        return RequestType.FETCH_MESSAGES;
    }

}
