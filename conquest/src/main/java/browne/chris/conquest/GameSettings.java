package browne.chris.conquest;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class GameSettings {
    @Value("${browne.chris.conquest.redis.connection-string}")
    private String redisConnectionString;
    @Value("${browne.chris.conquest.game.settings.base-troops-per-territory}")
    private long baseTroopsPerTerritory;
}
