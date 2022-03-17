package browne.chris.conquest.models;

import browne.chris.conquest.exceptions.InvalidCoordinatesException;
import browne.chris.conquest.exceptions.InvalidTileException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class Tile {
    private final Map<UUID, Long> troopPlacements;
    private Coordinate location;
    private Terrain terrain;

    public Tile() {
        troopPlacements = new HashMap<>();
    }

    public static Tile fromString(String value) throws InvalidTileException {
        String[] components = value.split("\\|");
        Tile t = new Tile();
        try {
            t.location = Coordinate.fromString(components[0]);
            t.terrain = new Terrain();
            t.terrain.setTerrainType(TerrainType.valueOf(components[1]));
            String[] placements = components[2].split(";");
            for (String placement :
                placements) {
                String[] placementDetails = placement.split(":");
                t.troopPlacements.put(UUID.fromString(placementDetails[0]),
                    Long.valueOf(placementDetails[1]));
            }
        } catch (InvalidCoordinatesException e) {
            throw new InvalidTileException(e);
        }
        return t;
    }

    public Coordinate getLocation() {
        return location;
    }

    public void deployTroops(Player player, long quantity) {
        troopPlacements.put(player.getId(), quantity);
    }

    public Optional<UUID> getControllingPlayer() {
        UUID controller = null;
        long maxTroops = 0;
        for (Map.Entry<UUID, Long> entry :
            troopPlacements.entrySet()) {
            if (entry.getValue() > maxTroops) {
                controller = entry.getKey();
                maxTroops = entry.getValue();
            }
        }
        return Optional.ofNullable(controller);
    }

    public boolean hasPlayerTroops(Player player, int quantity) {
        return
            troopPlacements.getOrDefault(player.getId(), 0L) > quantity;
    }

    public void removeTroops(Player player, int quantity) {
        long troops = troopPlacements.getOrDefault(player.getId(), 0L);
        troops -= quantity;
        if (troops < 0) {
            troops = 0;
        }
        troopPlacements.put(player.getId(), troops);
    }

    public boolean isSolid() {
        return terrain.isSolid();
    }

    public void settleBattles() {
        // todo: figure out how to settle territory battles
    }

    public long distanceTo(Tile target) {
        return location.distanceTo(target.location);
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Stream<Map.Entry<UUID, Long>> getTroopDeploymentStream() {
        return troopPlacements.entrySet().parallelStream();
    }
}
