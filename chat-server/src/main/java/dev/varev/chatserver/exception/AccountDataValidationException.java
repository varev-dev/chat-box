package dev.varev.chatserver.exception;

public class AccountDataValidationException extends RuntimeException {
    public AccountDataValidationException(String message) {
        super(message);
    }
}
