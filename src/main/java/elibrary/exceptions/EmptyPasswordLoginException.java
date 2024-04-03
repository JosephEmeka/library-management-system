package elibrary.exceptions;

public class EmptyPasswordLoginException extends RuntimeException {
    public EmptyPasswordLoginException(String message) {
        super(message);
    }
}
