package elibrary.exceptions;

public class BookUploadFailedException extends RuntimeException {
    public BookUploadFailedException(String message){
        super(message);
    }
}
