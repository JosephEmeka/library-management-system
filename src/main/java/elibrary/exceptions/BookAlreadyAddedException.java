package elibrary.exceptions;

public class BookAlreadyAddedException extends RuntimeException {
    public BookAlreadyAddedException(String message) {
        super(message);
    }
}
