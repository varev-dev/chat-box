package dev.varev.chatserver;

import java.time.LocalDateTime;
import java.util.UUID;

public class Account {
    private final long id;
    private final String username;
    private String password;
    private final byte[] salt;
    private final LocalDateTime createdAt;
    private LocalDateTime lastLogin;

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
    }

    public boolean verify(String password) {
        boolean verification = false;
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
}
