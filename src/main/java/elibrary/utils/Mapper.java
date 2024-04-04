package elibrary.utils;

import elibrary.data.model.Admin;
import elibrary.data.model.Book;
import elibrary.data.model.User;
import elibrary.dtos_requests.BookRegisterRequest;
import elibrary.dtos_requests.LogOutRequest;
import elibrary.dtos_requests.LoginRequest;
import elibrary.dtos_requests.RegisterRequest;
import elibrary.dtos_response.*;
import elibrary.enums.Categories;

import java.util.Optional;

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


    public static User userLoginMap(LoginRequest loginRequest){
            User user = new User();
            user.setUserName(loginRequest.getUsername());
            user.setPassword(loginRequest.getPassword());
            return user;
        }

        public static LoginResponse userLoginResponseMap(User user){
            LoginResponse userLoginResponse = new LoginResponse();
            userLoginResponse.setUserName(user.getUserName());
            userLoginResponse.setId(user.getUserId());
            return userLoginResponse;
        }

    public static User userLogOutMap(LogOutRequest logOutRequest){
        User user = new User();
        user.setUserName(logOutRequest.getUsername());
        user.setPassword(logOutRequest.getPassword());
        return user;
    }

    public static LogoutResponse userLogOutResponseMap(User user){
        LogoutResponse userLogoutResponse = new LogoutResponse();
        userLogoutResponse.setUserName(user.getUserName());
        userLogoutResponse.setId(user.getUserId());
        return  userLogoutResponse;
    }

    public static Admin AdminRegisterRequestMap(RegisterRequest newUserRegistrationRequest) {
       Admin admin = new Admin();
        admin.setUsername(newUserRegistrationRequest.getUserName());
        admin.setFirstName(newUserRegistrationRequest.getFirstName());
        admin.setLastName(newUserRegistrationRequest.getLastName());
        admin.setPassword(newUserRegistrationRequest.getPassword());
        admin.setEmail(newUserRegistrationRequest.getEmail());
        return admin;
    }
    public static RegisterResponse adminRegisterResponseMap(Admin admin) {
        RegisterResponse response = new RegisterResponse();
        response.setId(admin.getAdminId());
        response.setUserName(admin.getUsername());
        return response;
    }



    public static Admin adminLoginMap(LoginRequest loginRequest){
        Admin admin = new Admin();
        admin.setUsername(loginRequest.getUsername());
        admin.setPassword(loginRequest.getPassword());
        return admin;
    }

    public static LoginResponse adminLoginResponseMap(Admin admin){
        LoginResponse userLoginResponse = new LoginResponse();
        userLoginResponse.setUserName(admin.getUsername());
        userLoginResponse.setId(admin.getAdminId());
        return userLoginResponse;
    }

    public static Book bookRequestMap(BookRegisterRequest newBookRegistrationRequest) {
        Book book = new Book();
        book.setTitle(newBookRegistrationRequest.getTitle());
        book.setAuthor(newBookRegistrationRequest.getAuthor());
        book.setPublisher(newBookRegistrationRequest.getPublisher());
        book.setIsbn(newBookRegistrationRequest.getIsbn());
        book.setCategory(newBookRegistrationRequest.getCategory());
        return book;
    }

    public static BookRegisterResponse bookRegisterResponseMap(Book newBook) {
        BookRegisterResponse newResponse = new BookRegisterResponse();
        newResponse.setAuthor(newBook.getAuthor());
        newResponse.setTitle(newBook.getTitle());
        newResponse.setAvailable(newBook.isAvailable());
        return newResponse;
    }


    public static BookDeleteResponse deleteResponseMap(Book book) {
        return null;
    }

}



