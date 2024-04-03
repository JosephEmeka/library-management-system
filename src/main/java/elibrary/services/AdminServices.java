package elibrary.services;

import elibrary.dtos_requests.LoginRequest;
import elibrary.dtos_requests.RegisterRequest;
import elibrary.dtos_response.LoginResponse;
import elibrary.dtos_response.RegisterResponse;

public interface AdminServices {
    RegisterResponse registerAdmin(RegisterRequest newUserRegistrationRequest);
    LoginResponse loginAdmin(LoginRequest loginRequest);

}
