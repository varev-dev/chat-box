package dev.varev.chatserver.authentication;

import dev.varev.chatserver.account.AccountService;
import dev.varev.chatserver.connection.ConnectionManager;

import dev.varev.chatshared.dto.AuthenticationDTO;

public class AuthenticationService {
    private final AccountService accountService;
    private final ConnectionManager connectionManager;

    public AuthenticationService(AccountService accountService, ConnectionManager connectionManager) {
        this.accountService = accountService;
        this.connectionManager = connectionManager;
    }

    public boolean verify(AuthenticationDTO authenticationDTO) {
        return false;
    }

    public boolean register(String username, String password) {
        return false;
    }
}
