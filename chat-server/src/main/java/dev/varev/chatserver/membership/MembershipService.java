package dev.varev.chatserver.membership;

import dev.varev.chatserver.account.Account;
import dev.varev.chatserver.channel.Channel;

import java.time.Instant;
import java.util.Optional;

public class MembershipService {
    private final MembershipRepository repo;

    public MembershipService(MembershipRepository repo) {
        this.repo = repo;
    }

    protected Optional<Membership> getActiveMembership(Account account, Channel channel) {
        return repo.getMembershipIfActive(account, channel);
    }

    protected boolean addMembership(Account account, Channel channel) {
        var membership = createMembership(account, channel);
        return membership.filter(repo::addMembership).isPresent();
    }

    protected boolean leaveMembership(Account account, Channel channel) {
        var membership = repo.getMembershipIfActive(account, channel);

        membership.ifPresent(value -> value.setLeftAt(Instant.now()));
        return membership.isPresent();
    }

    protected boolean unblockMembership(Account account, Channel channel) {
        var membership = repo.getMembership(account, channel).filter(Membership::isBlocked);

        membership.ifPresent(value -> value.setBlocked(false));
        return membership.isPresent();
    }

    protected boolean blockMembership(Account account, Channel channel) {
        var membership = repo.getMembershipIfNotBlocked(account, channel);

        membership.ifPresent(value -> {
            value.setBlocked(true);
            value.setLeftAt(Instant.now());
        });

        return membership.isPresent();
    }

    private Optional<Membership> createMembership(Account account, Channel channel) {
        return Optional.of(new Membership(account, channel));
    }
}
