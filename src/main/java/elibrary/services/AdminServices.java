package elibrary.services;


import elibrary.dtos_requests.*;
import elibrary.dtos_response.*;
import org.springframework.stereotype.Service;

@Service
public interface AdminServices {
    RegisterResponse registerAdmin(RegisterRequest newUserRegistrationRequest);
    LoginResponse loginAdmin(LoginRequest loginRequest);
    BookRegisterResponse addBooks(BookRegisterRequest newBookRegistrationRequest);
    BookDeleteResponse deleteBooks(BookDeleteRequest newBookDeleteRequest);
    LogoutAdminResponse logoutAdmin(LogOutAdminRequest newLogOutRequest);



}
