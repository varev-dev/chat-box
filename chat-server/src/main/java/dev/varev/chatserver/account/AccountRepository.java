package dev.varev.chatserver.account;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AccountRepository {
    private final Set<Account> accounts;

    public AccountRepository() {
        this.accounts = new HashSet<>();
    }

    public AccountRepository(Set<Account> accounts) {
        this.accounts = accounts;
    }

    protected Optional<Account> getAccountWithUsername(String username) {
        return accounts.stream()
                .filter(account -> account.getUsername().equals(username))
                .findFirst();
    }

    protected Optional<Account> getAccountWithUsernameIfNotBlocked(String username) {
        var account = getAccountWithUsername(username);
        account.filter(Account::isBlocked)
                .filter(acc -> acc.getBlockedUntil().isBefore(Instant.now()))
                .ifPresent(Account::unblock);
        return account.filter(acc -> !acc.isBlocked());
    }

    protected boolean addAccount(Account account) {
        return accounts.add(account);
    }

    protected boolean removeAccount(Account account) {
        return accounts.remove(account);
    }
}
