package dev.varev.chatserver.account;

import dev.varev.chatshared.dto.AccountDTO;
import dev.varev.chatshared.dto.AuthenticationDTO;
import dev.varev.chatshared.dto.ErrorDTO;
import dev.varev.chatshared.response.Response;
import dev.varev.chatshared.response.ResponseCode;

import java.util.Optional;

public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public Response getAccountDetails(AccountDTO accountDTO) {
        var usernameValidationError = validateUsername(accountDTO.getUsername());
        if (usernameValidationError.isPresent())
            return usernameValidationError.get();

        return accountService.getAccountDetails(accountDTO);
    }

    public Response authenticate(AuthenticationDTO authDTO) {
        var valid = validateAuthenticationData(authDTO);
        if (valid.isPresent())
            return valid.get();
        
        return accountService.authenticate(authDTO);
    }

    public Response register(AuthenticationDTO authDTO) {
        var valid = validateAuthenticationData(authDTO);
        if (valid.isPresent())
            return valid.get();

        return accountService.register(authDTO);
    }
    
    private Optional<ErrorDTO> validateAuthenticationData(AuthenticationDTO authDTO) {
        var usernameValidationError = validateUsername(authDTO.getUsername());

        if (usernameValidationError.isPresent())
            return usernameValidationError;

        if (authDTO.getPassword().length() < AccountConstants.MINIMAL_PASSWORD_LENGTH)
            return Optional.of(new ErrorDTO(ResponseCode.UNAUTHORIZED, "Account password is too short."));
        
        return Optional.empty();
    }

    private Optional<ErrorDTO> validateUsername(String username) {
        if (username == null)
            return Optional.of(new ErrorDTO(ResponseCode.UNAUTHORIZED, "Username or password is missing."));

        if (username.length() < AccountConstants.MINIMAL_USERNAME_LENGTH)
            return Optional.of(new ErrorDTO(ResponseCode.UNAUTHORIZED, "Account username is too short."));

        if (!AccountConstants.USERNAME_PATTERN.matcher(username).matches())
            return Optional.of(new ErrorDTO(ResponseCode.UNAUTHORIZED, "Invalid account username."));

        return Optional.empty();
    }
}
