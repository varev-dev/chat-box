package dev.varev.chatshared.request;

import dev.varev.chatshared.MessageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class SendMessageRequest implements Request, Serializable {
    private final MessageDTO message;

    @Override
    public RequestType getType() {
        return RequestType.SEND_MESSAGE;
    }
}
