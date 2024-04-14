package elibrary.dtos_requests;

import lombok.Data;

@Data
public class LogOutAdminRequest {
    private String username;
    private String password;
}
