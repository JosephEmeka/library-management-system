package elibrary.dtos_response;

import lombok.Data;

@Data
public class BookRegisterResponse {
    private String author;
    private String title;
    private boolean isAvailable;

}
