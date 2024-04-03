package elibrary.exceptions;

public class WhiteSpaceException extends RuntimeException {
    public WhiteSpaceException(String userCannotEnterWhiteSpace) {
        super(userCannotEnterWhiteSpace);
    }
}
