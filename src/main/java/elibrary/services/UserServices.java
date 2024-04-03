package elibrary.services;

import elibrary.dtos_requests.LoginRequest;
import elibrary.dtos_requests.RegisterRequest;
import elibrary.dtos_response.LoginResponse;
import elibrary.dtos_response.RegisterResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserServices{
    public RegisterResponse registerUser(RegisterRequest newUserRegistrationRequest);
    public LoginResponse loginUser(LoginRequest loginRequest);

}
