package elibrary.exceptions;

public class EmptyUserNameLoginException extends RuntimeException {
    public EmptyUserNameLoginException(String message) {
        super (message);
    }
}
