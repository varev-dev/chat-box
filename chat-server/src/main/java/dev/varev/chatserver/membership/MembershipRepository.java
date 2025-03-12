package dev.varev.chatserver.membership;

import dev.varev.chatserver.account.Account;
import dev.varev.chatserver.channel.Channel;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MembershipRepository {
    private final Set<Membership> memberships;

    public MembershipRepository() {
        this.memberships = new HashSet<>();
    }

    public MembershipRepository(Set<Membership> memberships) {
        this.memberships = memberships;
    }

    protected Optional<Membership> getMembershipIfActive(Account account, Channel channel) {
        return getMembership(account, channel).filter(Membership::isActive);
    }

    protected Optional<Membership> getMembershipIfNotBlocked(Account account, Channel channel) {
        return getMembership(account, channel).filter(Membership::isBlocked);
    }

    protected Optional<Membership> getMembership(Account account, Channel channel) {
        return memberships.stream()
                .filter(membership -> membership.getAccount().equals(account))
                .filter(membership -> membership.getChannel().equals(channel))
                .max(Comparator.comparing(Membership::getJoinedAt));
    }

    protected boolean addMembership(Membership membership) {
        return memberships.add(membership);
    }
}
