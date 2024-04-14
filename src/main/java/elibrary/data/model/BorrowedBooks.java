package elibrary.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class BorrowedBooks{

    private String userId;
    private String bookId;
    @Id
    private String borrowerId;
    private String author;
    private String title;
    private String isbn;
    private String borrowerUsername;
    private LocalDateTime borrowedDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
}
