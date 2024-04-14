package elibrary.dtos_response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BorrowedBookRegisterResponse {
    private String title;
    private String  author;
    private String borrowerUserName;
    private boolean isAvailable;
    private LocalDateTime BorrowedDate;
    private LocalDateTime dueDate;
}
