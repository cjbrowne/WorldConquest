package browne.chris.conquest.services;

import browne.chris.conquest.GameSettings;
import browne.chris.conquest.models.Coordinate;
import browne.chris.conquest.models.Player;
import browne.chris.conquest.models.Tile;
import browne.chris.conquest.persisters.WorldPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class GameService {
    private static final long SECONDS_BETWEEN_TICKS = 30L;

    private final GameSettings settings;
    private final PlayerService playerService;
    private final WorldPersister worldPersister;
    private final WorldService worldService;
    private boolean frozen = false;


    @Autowired
    private GameService(GameSettings settings,
                        PlayerService playerService,
                        WorldPersister worldPersister,
                        WorldService worldService) {
        this.playerService = playerService;
        this.worldPersister = worldPersister;
        this.worldService = worldService;
        frozen = false;
        this.worldPersister.load(worldService);
        this.settings = settings;
    }

    @Scheduled(fixedRate = SECONDS_BETWEEN_TICKS, timeUnit = TimeUnit.SECONDS)
    public void tick() {
        frozen = true;
        Map<Player, Integer> territoryCounts = new HashMap<>();
        worldService.getTileStream()
            .filter(Tile::isSolid)
            .forEach(t -> {
                t.settleBattles();
                if (t.getControllingPlayer().isPresent()) {
                    int count =
                        territoryCounts.getOrDefault(playerService.get(t.getControllingPlayer()
                                    .get())
                                .orElseThrow(),
                            0);
                    territoryCounts.put(playerService.get(t.getControllingPlayer()
                                .get())
                            .orElseThrow(),
                        count + 1);
                }
            });
        playerService.stream().forEach(p -> {
            p.reinforce(territoryCounts.get(p) *
                settings.getBaseTroopsPerTerritory());
        });

        try {
            worldPersister.save(worldService);
        } catch (Throwable t) {
            t.printStackTrace();
            // safer to crash if unable to save world for any reason, to
            // reduce how much damage is done
            System.exit(-1);
        }
        frozen = false;
    }

    public boolean isFrozen() {
        return frozen;
    }

    /**
     *
     * @deprecated use WorldService.getTileByCoordinate instead.
     * @param target the location of the tile
     * @return the tile at this location
     */
    @Deprecated()
    public Tile getTileByCoordinate(Coordinate target) {
        return worldService.getTileByCoordinate(target);
    }
}
