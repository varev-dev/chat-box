package dev.varev.chatserver.account;

import dev.varev.chatshared.AccountDTO;

public class AccountMapper {
    public static AccountDTO toDTO(Account account) {
        return new AccountDTO(account.getUsername(), account.getCreatedAt(), account.getLastLogin());
    }
}
