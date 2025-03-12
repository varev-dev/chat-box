package dev.varev.chatserver.exception;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String username) {
        super("Account with '" + username + "' already exists.");
    }
}
