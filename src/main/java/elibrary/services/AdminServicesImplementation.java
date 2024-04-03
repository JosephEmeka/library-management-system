package elibrary.services;

import elibrary.data.model.Admin;
import elibrary.data.repository.AdminRepository;
import elibrary.dtos_requests.LoginRequest;
import elibrary.dtos_requests.RegisterRequest;
import elibrary.dtos_response.LoginResponse;
import elibrary.dtos_response.RegisterResponse;
import elibrary.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static elibrary.utils.Mapper.*;

@Service
public class AdminServicesImplementation implements AdminServices {
   private final AdminRepository adminRepository;
@Autowired
    public AdminServicesImplementation(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override

    public RegisterResponse registerAdmin(RegisterRequest newUserRegistrationRequest) {
        Admin admin = AdminRegisterRequestMap(newUserRegistrationRequest);
        validateAdmin(admin);
        adminRepository.save(admin);
        return adminRegisterResponseMap(admin);
    }



    private void validateAdmin(Admin admin) {
        if (admin.getUsername() == null || admin.getUsername().isEmpty()) {
            throw new EmptyUserNameRegistrationException("User name cannot be empty.");
        }
        if (admin.getUsername().equals(" ") || admin.getFirstName().equals(" ") || admin.getLastName().equals(" ") || admin.getPassword().equals(" ")) {
            throw new WhiteSpaceException("User cannot enter white Space");
        }
        if (admin.getFirstName().isEmpty())
            throw new EmptyFirstNameRegistrationException("First name cannot be empty.");
        if(admin.getLastName().isEmpty()) {
            throw new EmptyLastNameRegistrationException("Last name cannot be empty.");
        }

        Optional<Admin> existingUser = adminRepository.findByUsername(admin.getUsername());
        if (existingUser.isPresent()) {
            throw new DoubleUserRegistrationException("User with username " + admin.getUsername() + " already exists.");
        }
    }


    @Override
    public LoginResponse loginAdmin(LoginRequest loginRequest) {
       Admin admin = adminLoginMap(loginRequest);
        validateLoginRequest(loginRequest, admin);
        return adminLoginResponseMap(admin);
    }

    private void validateLoginRequest(LoginRequest loginRequest, Admin admin) {
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()) {
            throw new EmptyUserNameLoginException("User name cannot be empty.");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            throw new EmptyPasswordLoginException("Password cannot be empty.");
        }
        if (loginRequest.getUsername().equals(" ") || loginRequest.getPassword().equals(" ")) {
            throw new WhiteSpaceException("User cannot enter white Space");
        }
        Optional<Admin> existingAdminOptional = adminRepository.findByUsername(admin.getUsername());
        if (existingAdminOptional.isPresent()) {
            Admin existingUser = existingAdminOptional.get();
            if (!Objects.equals(existingUser.getPassword(), loginRequest.getPassword())) {
                throw new WrongPasswordException("Account does not exist or Invalid password");
            }
            existingUser.setIsLoggedIn(true);
            adminRepository.save(existingUser);
        }
        else
        {
            throw new NoSuchElementException("User not found");
        }


    }

}

