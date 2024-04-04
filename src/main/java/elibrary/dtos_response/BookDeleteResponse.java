package elibrary.dtos_response;

import lombok.Data;

@Data
public class BookDeleteResponse {
    private String bookTitle;
    private String bookAuthor;
}
