package elibrary.dtos_requests;

import elibrary.enums.Categories;
import lombok.Data;

@Data
public class BookDeleteRequest {
    private String author;
    private String title;
    private Categories bookCategory;

}
