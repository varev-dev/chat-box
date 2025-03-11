package dev.varev.chatserver.account;

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

    protected boolean addAccount(Account account) {
        return accounts.add(account);
    }

    protected boolean removeAccount(Account account) {
        return accounts.remove(account);
    }
}
