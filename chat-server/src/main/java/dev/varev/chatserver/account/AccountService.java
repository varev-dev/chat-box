package dev.varev.chatserver.account;

import dev.varev.chatserver.PasswordHasher;

import java.time.Instant;
import java.util.Optional;

public class AccountService {
    private final AccountRepository repo;

    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    public Optional<Account> verify(String username, String password) {
        var account = repo.getAccountWithUsernameIfNotBlocked(username);

        if (account.isEmpty())
            return Optional.empty();

        try {
            boolean verified = PasswordHasher.verifyPassword(password, account.get().getPassword(), account.get().getSalt());
            if (!verified)
                return Optional.empty();

            account.get().setLastLogin(Instant.now());
            return account;
        } catch (Exception e) {
            // TODO: Log verification exception
            return Optional.empty();
        }
    }

    public Optional<Account> register(String username, String password) {
        if (username.length() < AccountConstants.MINIMAL_USERNAME_LENGTH)
            return Optional.empty();

        if (!AccountConstants.USERNAME_PATTERN.matcher(username).matches())
            return Optional.empty();

        var account = repo.getAccountWithUsername(username);

        if (account.isPresent())
            return Optional.empty();

        if (password.length() < AccountConstants.MINIMAL_PASSWORD_LENGTH)
            return Optional.empty();

        return createAccount(username, password);
    }

    public boolean block(String username) {

        return false;
    }

    public boolean unblock(String username) {
        return false;
    }

    private Optional<Account> createAccount(String username, String password) {
        var account = new Account(username, password);
        return repo.addAccount(account) ? Optional.of(account) : Optional.empty();
    }
}
