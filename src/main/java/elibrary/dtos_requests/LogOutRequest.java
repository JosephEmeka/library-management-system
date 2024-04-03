package elibrary.dtos_requests;

import lombok.Data;

@Data
public class LogOutRequest {
    private String username;
    private String password;
}
