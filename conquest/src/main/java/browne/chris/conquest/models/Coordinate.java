package browne.chris.conquest.models;

import browne.chris.conquest.exceptions.InvalidCoordinatesException;

import java.io.Serializable;

public class Coordinate implements Serializable {
    private long x;
    private long y;

    public Coordinate() {
        this.x = 0;
        this.y = 0;
    }

    public Coordinate(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinate fromString(String key) throws InvalidCoordinatesException {
        Coordinate c = new Coordinate();
        try {
            String[] coords = key.replace("(", "").split(",");
            c.x = Long.parseLong(coords[0]);
            c.y = Long.parseLong(coords[1]);
        } catch (Throwable t) {
            throw new InvalidCoordinatesException(key, t);
        }
        return c;
    }

    public String toString() {
        return String.format("%d,%d", x, y);
    }

    public long distanceTo(Coordinate location) {
        long a = x - location.x;
        long b = y - location.y;
        // pythag saves the day
        double c = Math.sqrt((a * a) + (b * b));

        return Math.round(c);
    }
}
