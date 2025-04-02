package dev.varev.chatshared.request;

import dev.varev.chatshared.MessageDTO;

public class SendMessageRequest implements Request {
    private final MessageDTO message;

    public SendMessageRequest(MessageDTO message) {
        this.message = message;
    }

    @Override
    public RequestType getType() {
        return RequestType.SEND_MESSAGE;
    }

    public MessageDTO getMessage() {
        return message;
    }
}
