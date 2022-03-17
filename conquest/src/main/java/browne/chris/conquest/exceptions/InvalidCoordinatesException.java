package browne.chris.conquest.exceptions;

public class InvalidCoordinatesException extends
    Throwable {
    private final String raw;

    public InvalidCoordinatesException(String raw, Throwable t) {
        super(t);
        this.raw = raw;
    }

    public String getCoordinates() {
        return raw;
    }
}
