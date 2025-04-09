package dev.varev.chatserver.account;

import dev.varev.chatserver.membership.MembershipService;
import dev.varev.chatshared.dto.AccountDTO;
import dev.varev.chatshared.dto.AuthenticationDTO;
import dev.varev.chatshared.dto.ErrorDTO;

import dev.varev.chatshared.dto.ErrorCode;
import dev.varev.chatshared.response.FailedAuthResponse;
import dev.varev.chatshared.response.SuccessfulAuthResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Set;


public class AccountServiceTest {
    private AccountService service;

    @Mock
    private MembershipService membershipService;

    private static final Set<Account> accounts = new HashSet<>();

    @Before
    public void setUp() {
        var repo = new AccountRepository(accounts);
        service = new AccountService(repo, membershipService);
        accounts.add(new Account("exampleUser1", "testing"));
        accounts.add(new Account("Ex@mpl3User2", "loremIpsum"));
    }

    @Test
    public void testRegisterAccount() {
        var data = new AuthenticationDTO("ValidData@213", "ValidPassword");
        var result = service.register(data);

        Assert.assertTrue(result instanceof SuccessfulAuthResponse);
        Assert.assertEquals(data.getUsername(), ((SuccessfulAuthResponse) result).getAccount().getUsername());
    }

    @Test
    public void testRegisterAccountWithUsernameTaken() {
        if (accounts.isEmpty()) {
            Assert.fail("No account found.");
            return;
        }
        var account = accounts.stream().findFirst().get();
        var data = new AuthenticationDTO(account.getUsername(), "ValidPassword");
        var result = service.register(data);

        Assert.assertTrue(result instanceof FailedAuthResponse);
        Assert.assertEquals(ErrorCode.FORBIDDEN, ((FailedAuthResponse) result).getError().getCode());
    }

    @Test
    public void testAuthenticateAccount() {
        if (accounts.isEmpty()) {
            Assert.fail("No account found.");
            return;
        }

        var data = new AuthenticationDTO("exampleUser1", "testing");
        var result = service.authenticate(data);

        Assert.assertTrue(result instanceof SuccessfulAuthResponse);
        Assert.assertEquals(data.getUsername(), ((SuccessfulAuthResponse) result).getAccount().getUsername());
    }

    @Test
    public void testAuthenticateAccountThatDoesNotExist() {;
        var data = new AuthenticationDTO("NotFound", "RandomPassword");
        var result = service.authenticate(data);

        Assert.assertTrue(result instanceof FailedAuthResponse);
        Assert.assertEquals(ErrorCode.NOT_FOUND, ((FailedAuthResponse) result).getError().getCode());
    }
}
