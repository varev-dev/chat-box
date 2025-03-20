package dev.varev.chatserver.membership;

public class MembershipController {
    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    // TODO: join/leave channel -> create/deactivate membership
}
