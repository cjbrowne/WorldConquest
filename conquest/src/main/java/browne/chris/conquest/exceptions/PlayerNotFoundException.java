package browne.chris.conquest.exceptions;

import java.util.UUID;

public class PlayerNotFoundException extends
    Throwable {
    private final UUID uuid;

    public PlayerNotFoundException(UUID uuid) {
        super();
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }
}
