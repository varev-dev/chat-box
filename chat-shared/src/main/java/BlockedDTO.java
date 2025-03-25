import java.time.Instant;

public class BlockedDTO {
    private boolean blocked;
    private Instant blockedUntil;

    public BlockedDTO() {}

    public BlockedDTO(boolean blocked, Instant blockedUntil) {
        this.blocked = blocked;
        this.blockedUntil = blockedUntil;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Instant getBlockedUntil() {
        return blockedUntil;
    }

    public void setBlockedUntil(Instant blockedUntil) {
        this.blockedUntil = blockedUntil;
    }
}
