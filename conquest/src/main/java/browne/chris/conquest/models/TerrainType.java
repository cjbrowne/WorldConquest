package browne.chris.conquest.models;

public enum TerrainType {
    SEA(Double.POSITIVE_INFINITY),
    GRASSLAND(1),
    HILLS(1.5),
    MOUNTAIN(3);

    // for now, movementCost is not used, but it may be in the future
    double movementCost;

    TerrainType(double movementCost) {
        this.movementCost = movementCost;
    }
}
