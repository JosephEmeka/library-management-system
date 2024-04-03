package elibrary.dtos_response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private boolean isSuccessful;
    private Object data;

    @Data
    public static class RegisterRequestApiResponse {
        private String userName;
        private String id;


    }
}
