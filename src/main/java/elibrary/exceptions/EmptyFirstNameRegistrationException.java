package elibrary.exceptions;

public class EmptyFirstNameRegistrationException extends RuntimeException {
    public EmptyFirstNameRegistrationException(String message) {
        super(message);
    }
}
