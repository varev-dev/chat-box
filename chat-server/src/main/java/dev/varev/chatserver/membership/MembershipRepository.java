package dev.varev.chatserver.membership;

import java.util.HashSet;
import java.util.Set;

public class MembershipRepository {
    private final Set<Membership> memberships;

    public MembershipRepository() {
        this.memberships = new HashSet<>();
    }

    public MembershipRepository(Set<Membership> memberships) {
        this.memberships = memberships;
    }
}
