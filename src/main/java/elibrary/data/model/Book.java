package elibrary.data.model;

import elibrary.enums.Categories;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Book {
    @Id
    private String bookId;
    private String title;
    private String author;
    private String description;
    private String publisher;
    private String isbn;
    private boolean isAvailable = true;
    private Categories category;
}
