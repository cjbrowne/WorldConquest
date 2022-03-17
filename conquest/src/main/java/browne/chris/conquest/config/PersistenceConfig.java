package browne.chris.conquest.config;

import browne.chris.conquest.GameSettings;
import browne.chris.conquest.persisters.RedisWorldPersister;
import browne.chris.conquest.persisters.WorldPersister;
import browne.chris.conquest.serializers.TileSerializer;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfig {
    private final GameSettings gameSettings;
    private final TileSerializer tileSerializer;

    @Autowired
    public PersistenceConfig(GameSettings gameSettings,
                             TileSerializer tileSerializer) {
        this.gameSettings = gameSettings;
        this.tileSerializer = tileSerializer;
    }

    @Bean
    public WorldPersister redisWorldPersister() {
        RedisClient redisClient =
            RedisClient.create(gameSettings.getRedisConnectionString());
        StatefulRedisConnection<String, String> connection =
            redisClient.connect();
        return new RedisWorldPersister(connection, tileSerializer);
    }
}
