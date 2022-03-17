package browne.chris.conquest.serializers;

import browne.chris.conquest.models.Tile;

public class SimpleTileSerializer implements
    TileSerializer {
    @Override
    public String serialize(Tile t) {
        StringBuilder sb = new StringBuilder();
        sb.append(t.getLocation().toString());
        sb.append("|");
        sb.append(t.getTerrain().getTerrainType().toString());
        sb.append("|");
        t.getTroopDeploymentStream().forEach(e -> {
            sb.append(e.getKey());
            sb.append(":");
            sb.append(e.getValue());
        });
        return sb.toString();
    }
}
