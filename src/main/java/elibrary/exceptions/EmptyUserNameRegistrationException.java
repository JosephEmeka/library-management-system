package elibrary.exceptions;

public class EmptyUserNameRegistrationException extends RuntimeException {
    public EmptyUserNameRegistrationException(String message) {
        super(message);
    }
}
