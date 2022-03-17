package browne.chris.conquest.geo;

import browne.chris.conquest.models.Coordinate;
import lombok.Data;

@Data
public class Circle {
    private Coordinate origin;
    private long radius;

    public Circle(Coordinate origin, long radius) {
        this.origin = origin;
        this.radius = radius;
    }

    public boolean contains(Coordinate point) {
        return point.distanceTo(origin) <= radius;
    }
}
