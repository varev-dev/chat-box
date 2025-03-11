package dev.varev.chatserver.account;

import java.util.Optional;

public class AccountService {
    private final AccountRepository repo;

    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    public Optional<Account> verify(String username, String password) {
        var account = repo.getAccountWithUsername(username);
        return Optional.empty();
    }

    public Optional<Account> register(String username, String password) {
        var account = repo.getAccountWithUsername(username);
        return Optional.empty();
    }

    public boolean block(String username) {
        return false;

    }

    public boolean unblock(String username) {
        return false;
    }
}
