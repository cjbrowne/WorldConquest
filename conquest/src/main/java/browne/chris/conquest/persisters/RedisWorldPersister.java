package browne.chris.conquest.persisters;

import browne.chris.conquest.serializers.TileSerializer;
import browne.chris.conquest.services.WorldService;
import com.lambdaworks.redis.api.StatefulRedisConnection;

public class RedisWorldPersister implements WorldPersister {
    private static final String TILEMAP_PREFIX = "tm_";

    private final StatefulRedisConnection<String, String> connection;
    private final TileSerializer tileSerializer;

    public RedisWorldPersister(StatefulRedisConnection<String, String> connection,
                               TileSerializer tileSerializer) {
        this.connection = connection;
        this.tileSerializer = tileSerializer;
    }

    @Override
    public void save(WorldService world) {
        world.getTileStream().forEach(t -> {
            connection.async()
                .set(TILEMAP_PREFIX + t.getLocation().toString(),
                    tileSerializer.serialize(t));
        });
    }

    /**
     * Loads persistently saved data into the given world object
     *
     * @param world should be an initialised world object to load with data
     */
    @Override
    public void load(WorldService world) {

    }
}
