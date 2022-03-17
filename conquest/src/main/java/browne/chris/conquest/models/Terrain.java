package browne.chris.conquest.models;

public class Terrain {
    private TerrainType terrainType;

    public boolean isSolid() {
        return terrainType.movementCost != Double.POSITIVE_INFINITY;
    }

    public void setTerrainType(TerrainType tt) {
        terrainType = tt;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }
}
