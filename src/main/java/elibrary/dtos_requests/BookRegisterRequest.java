package elibrary.dtos_requests;

import lombok.Data;

@Data
public class BookRegisterRequest {
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String category;

}
