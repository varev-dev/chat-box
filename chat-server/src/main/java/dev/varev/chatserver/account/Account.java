package dev.varev.chatserver.account;

import dev.varev.chatserver.PasswordHasher;
import dev.varev.chatserver.exception.PasswordHashException;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Getter
public class Account {
    private final UUID id;
    private final String username;

    private String password;
    private final byte[] salt;

    @Setter
    private Instant lastLogin;
    private final Instant createdAt;

    private boolean blocked;
    private Instant blockedUntil;

    public Account(String username, String password) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.salt = PasswordHasher.generateSalt();

        try {
            this.password = PasswordHasher.hashPassword(password, salt);
        } catch (Exception e) {
            // TODO: throw Password hashing exception with an information
            throw new PasswordHashException(this.id);
        }

        this.createdAt = Instant.now();
        this.lastLogin = createdAt;
        this.blocked = false;
        this.blockedUntil = null;
    }

    public void setPassword(String password) {
        try {
            this.password = PasswordHasher.hashPassword(password, salt);
        } catch (Exception e) {
            // TODO: throw Password hashing exception with an information
            throw new PasswordHashException(this.id);
        }
    }

    protected byte[] getSalt() {
        return this.salt;
    }

    public void unblock() {
        this.blocked = false;
    }

    public void block(Duration duration) {
        this.blocked = true;
        this.blockedUntil = Instant.now().plus(duration);
    }

}
