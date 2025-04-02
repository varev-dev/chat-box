package dev.varev.chatshared.request;

public enum RequestType {
    REGISTER,
    AUTHENTICATION,

    SEND_MESSAGE,
    FETCH_MESSAGES,

    JOIN_CHANNEL,
    LEAVE_CHANNEL,
    PUBLIC_CHANNELS,
}
