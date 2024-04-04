package elibrary.exceptions;

public class EmptyRegistrationEntryException extends RuntimeException {
    public EmptyRegistrationEntryException(String message) {
        super(message);
    }
}
