package dev.varev.chatshared;

public enum ResponseCode {
    OK(200, "Ok"),
    NOT_FOUND(404, "Not Found"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),;

    public final int code;
    public final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
