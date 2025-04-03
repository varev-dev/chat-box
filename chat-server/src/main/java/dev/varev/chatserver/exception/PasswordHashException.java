package dev.varev.chatserver.exception;

import java.util.UUID;

public class PasswordHashException extends RuntimeException {
    public PasswordHashException(UUID userId) {
        super("Password hashing failed for user " + userId.toString());
    }
}
