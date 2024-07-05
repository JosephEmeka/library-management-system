package elibrary.dtos_response;

import com.fasterxml.jackson.annotation.JsonProperty;
import elibrary.enums.Category;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UploadBookResponse {
    private String BookId;
    @JsonProperty("media_url")
    private String url;
    @JsonProperty("media_description")
    private String description;
    private Category category;
}
