package elibrary.dtos_requests;

import lombok.Data;

@Data
public class BorrowedBookDeleteRequest {
    private String title;
    private String author;
    private String borrowerUserName;
}
