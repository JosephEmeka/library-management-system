package elibrary.exceptions;

public class AlreadyLoggedInException extends RuntimeException {
    public AlreadyLoggedInException(String message ) {
        super(message);
    }
}
