package elibrary.dtos_requests;


import lombok.Data;


import java.time.LocalDateTime;

@Data
public class BorrowedBookRegisterRequest {
    private String title;
    private String author;
    private String borrowerUserName;
    private LocalDateTime borrowedDate = LocalDateTime.now();
    private LocalDateTime dueDate = LocalDateTime.now().plusDays(30);
}
