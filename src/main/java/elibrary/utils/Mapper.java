package elibrary.utils;

import elibrary.data.model.User;
import elibrary.dtos_requests.RegisterRequest;
import elibrary.dtos_response.RegisterResponse;

public class Mapper {
    public static User RegisterRequestMap(RegisterRequest newUserRegistrationRequest) {
        User user = new User();
        user.setUserName(newUserRegistrationRequest.getUserName());
        user.setFirstName(newUserRegistrationRequest.getFirstName());
        user.setLastName(newUserRegistrationRequest.getLastName());
        user.setPassword(newUserRegistrationRequest.getPassword());
        user.setEmail(newUserRegistrationRequest.getEmail());
        return user;
    }

    public static RegisterResponse RegisterResponseMap(User user) {
        RegisterResponse response = new RegisterResponse();
        response.setId(user.getUserId());
        response.setUserName(user.getUserName());
        return response;
    }

}
