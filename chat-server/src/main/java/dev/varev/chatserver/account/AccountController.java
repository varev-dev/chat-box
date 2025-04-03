package dev.varev.chatserver.account;

import dev.varev.chatshared.AuthenticationDTO;
import dev.varev.chatshared.ErrorDTO;
import dev.varev.chatshared.ResponseCode;
import dev.varev.chatshared.Response;

import java.util.Optional;

public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
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
        if (authDTO.getUsername() == null || authDTO.getPassword() == null)
            return Optional.of(new ErrorDTO(ResponseCode.UNAUTHORIZED, "Username or password is missing."));
        
        if (authDTO.getUsername().length() < AccountConstants.MINIMAL_USERNAME_LENGTH)
            return Optional.of(new ErrorDTO(ResponseCode.UNAUTHORIZED, "Account username is too short."));
        
        if (!AccountConstants.USERNAME_PATTERN.matcher(authDTO.getUsername()).matches())
            return Optional.of(new ErrorDTO(ResponseCode.UNAUTHORIZED, "Invalid account username."));
        
        if (authDTO.getPassword().length() < AccountConstants.MINIMAL_PASSWORD_LENGTH)
            return Optional.of(new ErrorDTO(ResponseCode.UNAUTHORIZED, "Account password is too short."));
        
        return Optional.empty();
    }
}
