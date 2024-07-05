package elibrary.dtos_requests;

import elibrary.enums.Category;
import lombok.Data;

@Data
public class BookDeleteRequest {
    private String author;
    private String title;
    private String isbn;
    private Category bookCategory;
}
