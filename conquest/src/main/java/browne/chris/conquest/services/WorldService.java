package browne.chris.conquest.services;

import browne.chris.conquest.geo.Circle;
import browne.chris.conquest.models.Coordinate;
import browne.chris.conquest.models.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WorldService {
    private static final Logger log =
        LoggerFactory.getLogger(WorldService.class);
    private Map<Coordinate, Tile> tileMap;

    public WorldService() {
        tileMap = new HashMap<>();
    }

    public Stream<Tile> getTileStream() {
        return tileMap.values().parallelStream();
    }

    public Tile getTileByCoordinate(Coordinate target) {
        return tileMap.get(target);
    }

    public List<Tile> getTilesInCircle(Circle c) {
        return tileMap.entrySet()
            .parallelStream()
            .filter(t -> c.contains(t.getKey()))
            .map(Map.Entry::getValue)
            .collect(Collectors.toList());
    }
}
