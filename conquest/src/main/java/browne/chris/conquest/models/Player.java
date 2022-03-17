package browne.chris.conquest.models;

import lombok.Data;

import java.util.UUID;

@Data
public class Player {
    private UUID id;
    private long idleTroops;
    private String nick;

    public boolean is(Player other) {
        return id == other.id;
    }

    /**
     * In the future, you may have more available troops than just idle
     * but for now available==idle.
     *
     * @return all available troops
     */
    public long getAvailableTroops() {
        return idleTroops;
    }

    public void reinforce(long troops) {
        idleTroops += troops;
    }
}
