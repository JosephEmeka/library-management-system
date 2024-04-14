package elibrary.dtos_response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BorrowedBookAdditionResponse {
    private String title;
    private String  author;
    private boolean isAvailable;
    private LocalDateTime BorrowedDate;
    private LocalDateTime dueDate;
}
