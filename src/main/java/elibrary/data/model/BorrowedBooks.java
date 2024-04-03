package elibrary.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class BorrowedBooks {

    private String userId;
    private String bookId;
    @Id
    private String borrowerId;
    private String borrowedDate;
    private String dueDate;
    private String returnDate;
}
