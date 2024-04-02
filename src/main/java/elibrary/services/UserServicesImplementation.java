package elibrary.services;

import elibrary.data.model.User;
import elibrary.data.repository.UserRepository;
import elibrary.dtos_requests.RegisterRequest;
import elibrary.dtos_response.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static elibrary.utils.Mapper.RegisterRequestMap;
import static elibrary.utils.Mapper.RegisterResponseMap;

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

    public RegisterResponse registerUser(RegisterRequest newUserRegistrationRequest) {
        User user = RegisterRequestMap(newUserRegistrationRequest);
        userRepository.save(user);
        return RegisterResponseMap(user);
    }



}
