package elibrary.utils;

import elibrary.data.model.Admin;
import elibrary.data.model.Book;
import elibrary.data.model.BorrowedBooks;
import elibrary.data.model.User;
import elibrary.dtos_requests.*;
import elibrary.dtos_response.*;

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
        BookDeleteResponse deleteResponse = new BookDeleteResponse();
        deleteResponse.setBookAuthor(book.getAuthor());
        deleteResponse.setBookTitle(book.getTitle());
        return deleteResponse;
    }
    public static Book bookDeleteRequestMap(BookDeleteRequest bookDeleteRequest) {
        Book book = new Book();
        book.setTitle(bookDeleteRequest.getTitle());
        book.setAuthor(bookDeleteRequest.getAuthor());
        book.setCategory(bookDeleteRequest.getBookCategory());
        book.setIsbn(bookDeleteRequest.getIsbn());
        return book;
    }

    public static Admin adminLoginOutMap(LogOutAdminRequest loginOutAdminRequest){
        Admin admin = new Admin();
        admin.setUsername(loginOutAdminRequest.getUsername());
        admin.setPassword(loginOutAdminRequest.getPassword());
        return admin;
    }
    public static LogoutAdminResponse logOutAdminResponseMap(Admin admin){
        LogoutAdminResponse logoutAdminResponse = new LogoutAdminResponse();
        logoutAdminResponse.setUserName(admin.getUsername());
       logoutAdminResponse.setId(admin.getAdminId());
        return  logoutAdminResponse;
    }


    public static BorrowedBooks borrowedBookRequestMap(BorrowedBookRegisterRequest borrowedBookRegisterRequest){
        BorrowedBooks borrowedBook = new BorrowedBooks();
        borrowedBook.setTitle(borrowedBookRegisterRequest.getTitle());
        borrowedBook.setAuthor(borrowedBookRegisterRequest.getAuthor());
        borrowedBook.setBorrowedDate(borrowedBookRegisterRequest.getBorrowedDate());
        borrowedBook.setDueDate(borrowedBookRegisterRequest.getDueDate());
        borrowedBook.setBorrowerUsername(borrowedBookRegisterRequest.getBorrowerUserName());
        return borrowedBook;
    }

    public static BorrowedBookRegisterResponse borrowedBookRegisterResponseMap(BorrowedBooks borrowedBook){
        BorrowedBookRegisterResponse borrowedBookRegisterResponse = new BorrowedBookRegisterResponse();
        borrowedBookRegisterResponse.setAuthor(borrowedBook.getAuthor());
        borrowedBookRegisterResponse.setTitle(borrowedBook.getTitle());
        borrowedBookRegisterResponse.setBorrowerUserName(borrowedBook.getBorrowerUsername());
        borrowedBookRegisterResponse.setBorrowedDate(borrowedBook.getBorrowedDate());
        borrowedBookRegisterResponse.setDueDate(borrowedBook.getDueDate());
        return borrowedBookRegisterResponse;

    }

    public static BorrowedBookDeleteResponse borrowedBookDeleteResponseMap(BorrowedBooks borrowedBooks){
        BorrowedBookDeleteResponse borrowedBookDeleteResponse = new BorrowedBookDeleteResponse();
        borrowedBookDeleteResponse.setAuthor(borrowedBooks.getAuthor());
        borrowedBookDeleteResponse.setTitle(borrowedBooks.getTitle());
        return borrowedBookDeleteResponse;

    }
}



