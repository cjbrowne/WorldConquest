package browne.chris.conquest.queue;

import lombok.Data;

import java.util.UUID;

@Data
public class PlayerManagementMessage {
    private UUID playerId;
    private PlayerManagementAction action;
    // meaning varies
    private String payload;
}
