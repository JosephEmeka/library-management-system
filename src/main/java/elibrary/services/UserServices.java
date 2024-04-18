package elibrary.services;

import elibrary.data.model.Book;
import elibrary.data.model.BorrowedBooks;
import elibrary.dtos_requests.*;
import elibrary.dtos_response.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServices{
    public RegisterResponse registerUser(RegisterRequest newUserRegistrationRequest);
    public LoginResponse loginUser(LoginRequest loginRequest);
    List<Book> getAllBooks();
    List<BorrowedBooks> getAllBorrowedBooks(BorrowedBookRegisterRequest borrowBookRequest);
    BorrowedBookRegisterResponse borrowBooks(BorrowedBookRegisterRequest newBorrowedBookRegistrationRequest);
    public BorrowedBookDeleteResponse deleteBook(BorrowedBookDeleteRequest deleteBookRequest);
    LogoutResponse logoutUser(LogOutRequest newLogOutRequest);
}
