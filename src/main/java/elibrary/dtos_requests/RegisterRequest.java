package elibrary.dtos_requests;

import lombok.Data;

@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;

}
