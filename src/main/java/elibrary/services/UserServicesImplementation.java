package elibrary.services;

import elibrary.data.model.User;
import elibrary.data.repository.UserRepository;
import elibrary.dtos_requests.LogOutRequest;
import elibrary.dtos_requests.LoginRequest;
import elibrary.dtos_requests.RegisterRequest;
import elibrary.dtos_response.LoginResponse;
import elibrary.dtos_response.LogoutResponse;
import elibrary.dtos_response.RegisterResponse;
import elibrary.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static elibrary.utils.Mapper.*;

@Service
public class UserServicesImplementation implements UserServices{
    private final UserRepository userRepository;
@Autowired
    public UserServicesImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public RegisterResponse registerUser(RegisterRequest newUserRegistrationRequest) {
        User user = RegisterRequestMap(newUserRegistrationRequest);
        validateUser(user);
        userRepository.save(user);
        return RegisterResponseMap(user);
    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        User user = userLoginMap(loginRequest);
        validateLoginRequest(loginRequest, user);
        return userLoginResponseMap(user);
    }

    private void validateLoginRequest(LoginRequest loginRequest, User user) {
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()) {
            throw new EmptyUserNameLoginException("User name cannot be empty.");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            throw new EmptyPasswordLoginException("Password cannot be empty.");
        }
        if (loginRequest.getUsername().equals(" ") || loginRequest.getPassword().equals(" ")) {
            throw new WhiteSpaceException("User cannot enter white Space");
        }
        Optional<User> existingUserOptional = userRepository.findByUserName(user.getUserName());
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            if (!Objects.equals(existingUser.getPassword(), loginRequest.getPassword())) {
                throw new WrongPasswordException("Account does not exist or Invalid password");
            }
            existingUser.setIsLoggedIn(true);
            userRepository.save(existingUser);
        }
        else
        {
            throw new NoSuchElementException("User not found");
        }


    }

    private void validateUser(User user)  {
        if (user.getUserName() == null || user.getUserName().isEmpty()) {
            throw new EmptyUserNameRegistrationException("User name cannot be empty.");
        }
        if (user.getUserName().equals(" ") || user.getFirstName().equals(" ") || user.getLastName().equals(" ") || user.getPassword().equals(" ")) {
            throw new WhiteSpaceException("User cannot enter white Space");
        }
        if (user.getFirstName().isEmpty())
            throw new EmptyFirstNameRegistrationException("First name cannot be empty.");
        if(user.getLastName().isEmpty()) {
            throw new EmptyLastNameRegistrationException("Last name cannot be empty.");
        }

        Optional<User> existingUser = userRepository.findByUserName(user.getUserName());
        if (existingUser.isPresent()) {
            throw new DoubleUserRegistrationException("User with username " + user.getUserName() + " already exists.");
        }
    }
    public LogoutResponse logoutUser(LogOutRequest newLogOutRequest) {
        User user = userLogOutMap(newLogOutRequest);
        validateLogoutRequest(newLogOutRequest, user);
        return userLogOutResponseMap(user);
    }

    private void validateLogoutRequest(LogOutRequest logOutRequest, User user) {
        AdminServicesImplementation.requestCheck(logOutRequest.getUsername(), logOutRequest.getPassword());
        Optional<User> existingUserOptional = userRepository.findByUserName(user.getUserName());
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            if (!Objects.equals(existingUser.getPassword(), logOutRequest.getPassword())) {
                throw new WrongPasswordException("Account does not exist or Invalid password");
            }
            existingUser.setIsLoggedIn(false);
            userRepository.save(existingUser);
        } else {
            throw new NoSuchElementException("User not found");
        }
    }


}
