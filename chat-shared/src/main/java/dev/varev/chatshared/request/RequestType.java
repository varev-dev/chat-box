package dev.varev.chatshared.request;

public enum RequestType {
    REGISTER,
    AUTHENTICATION,

    GET_ACCOUNT_DETAILS,

    SEND_MESSAGE,
    FETCH_MESSAGES,

    JOIN_CHANNEL,
    LEAVE_CHANNEL,
    PUBLIC_CHANNELS,

    EXIT
}
