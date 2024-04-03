package elibrary.dtos_requests;

import lombok.Data;

@Data
public class LoginRequest {
    public String username;
    public String password;
}
