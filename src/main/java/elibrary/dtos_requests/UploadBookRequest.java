package elibrary.dtos_requests;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadBookRequest {
    private String adminEmail;
    private String title;
    private String description;
    private String Author;
    private MultipartFile mediaFile;
}
