package dev.varev.chatserver.account;

import dev.varev.chatserver.PasswordHasher;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class Account {
    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);
    private final long id;
    private final String username;
    private String password;
    private final byte[] salt;

    private final LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    private boolean isBlocked;
    private LocalDateTime blockedUntil;

    public Account(String username, String password) {
        this.id = UUID.randomUUID().getMostSignificantBits();
        this.username = username;
        this.salt = PasswordHasher.generateSalt();

        try {
            this.password = PasswordHasher.hashPassword(password, salt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.createdAt = LocalDateTime.now();
        this.lastLogin = createdAt;
        this.isBlocked = false;
        this.blockedUntil = null;
    }

    public boolean verify(String password) {
        boolean verification = false;

        if (this.isBlocked) {
            if (this.blockedUntil.isAfter(LocalDateTime.now())) {
                return false;
            } else {
                this.isBlocked = false;
            }
        }

        try {
            verification = PasswordHasher.verifyPassword(password, this.password, salt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!verification)
            return false;

        this.lastLogin = LocalDateTime.now();
        return true;
    }

    public void changePassword(String newPassword) {
        try {
            this.password = PasswordHasher.hashPassword(newPassword, salt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public void block(Duration duration) {
        this.isBlocked = true;
        this.blockedUntil = LocalDateTime.now().plus(duration);
    }
}
