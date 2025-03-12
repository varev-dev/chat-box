package dev.varev.chatserver.account;

import dev.varev.chatserver.PasswordHasher;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class Account {
    private final UUID id;
    private final String username;
    private String password;
    private final byte[] salt;

    private final Instant createdAt;
    private Instant lastLogin;

    private boolean blocked;
    private Instant blockedUntil;

    public Account(String username, String password) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.salt = PasswordHasher.generateSalt();

        try {
            this.password = PasswordHasher.hashPassword(password, salt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.createdAt = Instant.now();
        this.lastLogin = createdAt;
        this.blocked = false;
        this.blockedUntil = null;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        try {
            this.password = PasswordHasher.hashPassword(password, salt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected byte[] getSalt() {
        return this.salt;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void unblock() {
        this.blocked = false;
    }

    public void block(Duration duration) {
        this.blocked = true;
        this.blockedUntil = Instant.now().plus(duration);
    }

    public Instant getBlockedUntil() {
        return blockedUntil;
    }

    public Instant getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Instant lastLogin) {
        this.lastLogin = lastLogin;
    }
}
