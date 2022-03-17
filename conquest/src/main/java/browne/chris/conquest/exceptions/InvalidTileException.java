package browne.chris.conquest.exceptions;

public class InvalidTileException extends
    Throwable {
    public InvalidTileException(InvalidCoordinatesException e) {
        super(e);
    }
}
