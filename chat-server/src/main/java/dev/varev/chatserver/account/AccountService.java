package dev.varev.chatserver.account;

import dev.varev.chatserver.PasswordHasher;
import dev.varev.chatshared.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Optional;

public class AccountService {
    private final AccountRepository repo;

    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    public Response authenticate(AuthenticationDTO auth) {
        String username = auth.getUsername();
        String password = auth.getPassword();

        var account = repo.getAccountWithUsername(username);

        if (account.isEmpty())
            return new ErrorDTO(ResponseCode.NOT_FOUND, "Account not found.");

        if (account.get().isBlocked())
            return new ErrorDTO(ResponseCode.UNAUTHORIZED, "Account is locked up to " +
                    new DateTimeFormatterBuilder().appendPattern("dd-MM-yyyy HH:mm:ss").toFormatter().format(account.get().getBlockedUntil()) + ".");

        try {
            boolean verified = PasswordHasher.verifyPassword(password, account.get().getPassword(), account.get().getSalt());
            if (!verified)
                return new ErrorDTO(ResponseCode.UNAUTHORIZED, "Invalid password.");

            account.get().setLastLogin(Instant.now());
            return AccountMapper.toDTO(account.get());
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            // TODO: Log verification exception
            return new ErrorDTO(ResponseCode.FORBIDDEN, "Authentication failed.");
        }
    }

    public Response register(AuthenticationDTO authDTO) {
        String username = authDTO.getUsername();
        String password = authDTO.getPassword();

        var account = repo.getAccountWithUsername(username);

        if (account.isPresent())
            return new ErrorDTO(ResponseCode.UNAUTHORIZED, "Account with given name exists.");

        account = createAccount(username, password);

        if (account.isEmpty())
            return new ErrorDTO(ResponseCode.UNAUTHORIZED, "Account creation failed.");

        return AccountMapper.toDTO(account.get());
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
