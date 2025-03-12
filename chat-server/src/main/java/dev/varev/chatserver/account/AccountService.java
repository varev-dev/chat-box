package dev.varev.chatserver.account;

import dev.varev.chatserver.PasswordHasher;
import dev.varev.chatserver.exception.AccountAlreadyExistsException;
import dev.varev.chatserver.exception.AccountDataValidationException;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Optional;

public class AccountService {
    private final AccountRepository repo;

    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    public Optional<Account> verify(String username, String password)
            throws AccountNotFoundException, CredentialException, AccountLockedException {
        var account = repo.getAccountWithUsername(username);

        if (account.isEmpty())
            throw new AccountNotFoundException("Account with provided username not found.");

        if (account.get().isBlocked())
            throw new AccountLockedException("Account is locked up to " + new DateTimeFormatterBuilder()
                    .appendPattern("dd-MM-yyyy HH:mm:ss").toFormatter().format(account.get().getBlockedUntil()) + ".");

        try {
            boolean verified = PasswordHasher.verifyPassword(password, account.get().getPassword(), account.get().getSalt());
            if (!verified)
                throw new CredentialException("Account verification failed due to wrong password.");

            account.get().setLastLogin(Instant.now());
            return account;
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            // TODO: Log verification exception
            return Optional.empty();
        }
    }

    public Optional<Account> register(String username, String password)
            throws AccountDataValidationException, AccountAlreadyExistsException {
        if (username.length() < AccountConstants.MINIMAL_USERNAME_LENGTH)
            throw new AccountDataValidationException("Account username has to be at least " +
                    AccountConstants.MINIMAL_USERNAME_LENGTH + " characters.");

        if (!AccountConstants.USERNAME_PATTERN.matcher(username).matches())
            throw new AccountDataValidationException("Account username contains invalid characters.");

        var account = repo.getAccountWithUsername(username);

        if (account.isPresent())
            throw new AccountAlreadyExistsException(username);

        if (password.length() < AccountConstants.MINIMAL_PASSWORD_LENGTH)
            throw new AccountDataValidationException("Account password has to vbe at least " +
                    AccountConstants.MINIMAL_PASSWORD_LENGTH + " characters.");

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
