package dev.varev.chatserver.account;

import dev.varev.chatserver.PasswordHasher;
import dev.varev.chatserver.channel.Channel;
import dev.varev.chatserver.membership.MembershipService;
import dev.varev.chatshared.dto.*;
import dev.varev.chatshared.response.*;
import dev.varev.chatshared.dto.ErrorCode;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Optional;

public class AccountService {
    private final AccountRepository repo;
    private final MembershipService membershipService;

    public AccountService(AccountRepository repo, MembershipService membershipService) {
        this.repo = repo;
        this.membershipService = membershipService;
    }

    public Response authenticate(AuthenticationDTO auth) {
        String username = auth.getUsername();
        String password = auth.getPassword();

        var account = repo.getAccountWithUsername(username);

        if (account.isEmpty())
            return new FailedAuthResponse(new ErrorDTO(ErrorCode.NOT_FOUND, "Account not found."), ResponseCode.FAILED);

        if (account.get().isBlocked())
            return new FailedAuthResponse(
                    new ErrorDTO(ErrorCode.UNAUTHORIZED, "Account is locked up to " + new DateTimeFormatterBuilder()
                        .appendPattern("dd-MM-yyyy HH:mm:ss").toFormatter().format(account.get().getBlockedUntil()) + "."),
                    ResponseCode.FAILED);

        try {
            boolean verified = PasswordHasher.verifyPassword(password, account.get().getPassword(), account.get().getSalt());
            if (!verified)
                return new FailedAuthResponse(new ErrorDTO(ErrorCode.UNAUTHORIZED, "Invalid password."), ResponseCode.FAILED);

            account.get().setLastLogin(Instant.now());
            return new SuccessfulAuthResponse(AccountMapper.toDTO(account.get()));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            // TODO: Log verification exception
            return new FailedAuthResponse(new ErrorDTO(ErrorCode.FORBIDDEN, "Authentication failed."), ResponseCode.SERVER_ERROR);
        }
    }

    public Response register(AuthenticationDTO authDTO) {
        String username = authDTO.getUsername();
        String password = authDTO.getPassword();

        var account = repo.getAccountWithUsername(username);

        if (account.isPresent())
            return new FailedAuthResponse(new ErrorDTO(ErrorCode.FORBIDDEN, "Account with given name exists."), ResponseCode.FAILED);

        account = createAccount(username, password);

        if (account.isEmpty())
            return new FailedAuthResponse(new ErrorDTO(ErrorCode.UNAUTHORIZED, "Account creation failed."), ResponseCode.FAILED);

        return new SuccessfulAuthResponse(AccountMapper.toDTO(account.get()));
    }

    protected Response getAccountDetails(AccountDTO accountDTO) {
        var account = repo.getAccountWithUsername(accountDTO.getUsername());

        if (account.isEmpty())
            return new FailedAuthResponse(new ErrorDTO(ErrorCode.NOT_FOUND, "Account not found."), ResponseCode.FAILED);

        var channels = new ArrayList<Channel>();
        membershipService.getActiveMembershipsByAccount(account.get())
                .forEach(e -> channels.add(e.getChannel()));

        var channelsDTOs = new ArrayList<ChannelDTO>();
        channels.forEach(e -> channelsDTOs.add(new ChannelDTO(e.getName(), e.getCreatedAt())));

        return new AccountDetailsResponse(new AccountDetailsDTO(AccountMapper.toDTO(account.get()), channelsDTOs));
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
