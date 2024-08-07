package elibrary.dtos_requests;

import elibrary.enums.Category;
import lombok.Data;

@Data
public class BookRegisterRequest {
    private String bookId;
    private String title;
    private String author;
    private String description;
    private String publisher;
    private String isbn;
    private boolean isAvailable = true;
    private Category category;
}
