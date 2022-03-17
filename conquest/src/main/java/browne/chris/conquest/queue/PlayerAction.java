package browne.chris.conquest.queue;

import browne.chris.conquest.models.Coordinate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class PlayerAction {
    @JsonProperty(required = true)
    private UUID playerId;
    @JsonProperty(required = true)
    private ActionType action;
    @JsonProperty(required = false)
    private Coordinate target;
    @JsonProperty(required = false)
    private Coordinate source;
    @JsonProperty(required = false)
    private Long amount;
}
