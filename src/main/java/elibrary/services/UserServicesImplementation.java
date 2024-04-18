package elibrary.services;

import elibrary.data.model.Book;
import elibrary.data.model.BorrowedBooks;
import elibrary.data.model.User;
import elibrary.data.repository.BookRepository;
import elibrary.data.repository.BorrowedBooksRepository;
import elibrary.data.repository.UserRepository;
import elibrary.dtos_requests.*;
import elibrary.dtos_response.*;
import elibrary.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static elibrary.utils.Mapper.*;

@Service
public class UserServicesImplementation implements UserServices{
    @Autowired
        private  BorrowedBooksRepository borrowedBooksRepository;
    @Autowired
        private BookRepository bookRepository;
        private final UserRepository userRepository;
        @Autowired
        private BorrowedBookServices borrowedBookServices;

        @Autowired
        public UserServicesImplementation(UserRepository userRepository) {
            this.userRepository = userRepository;

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
        validateLoginRequest(loginRequest);
        return userLoginResponseMap(validateLoginRequest(loginRequest));
    }

    private User validateLoginRequest(LoginRequest loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()) {
            throw new EmptyUserNameLoginException("User name cannot be empty.");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            throw new EmptyPasswordLoginException("Password cannot be empty.");
        }
        if (loginRequest.getUsername().equals(" ") || loginRequest.getPassword().equals(" ")) {
            throw new WhiteSpaceException("User cannot enter white Space");
        }
        Optional<User> existingUserOptional = userRepository.findByUserName(loginRequest.getUsername());
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            if (!Objects.equals(existingUser.getPassword(), loginRequest.getPassword())) {
                throw new WrongPasswordException("Invalid password");
            }
            existingUser.setIsLoggedIn(true);
            userRepository.save(existingUser);
            return existingUser;
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

    @Override
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

@Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
@Override
    public List<BorrowedBooks> getAllBorrowedBooks(BorrowedBookRegisterRequest borrowBookRequest) {
        return borrowedBooksRepository.findAllByBorrowerUsername(borrowBookRequest.getBorrowerUserName());
    }
@Override
    public BorrowedBookRegisterResponse borrowBooks(BorrowedBookRegisterRequest newBorrowedBookRegistrationRequest) {
        return borrowedBookServices.addBorrowedBook(newBorrowedBookRegistrationRequest);
    }
@Override
    public BorrowedBookDeleteResponse deleteBook(BorrowedBookDeleteRequest deleteBookRequest) {
        return borrowedBookServices.returnBorrowedBook(deleteBookRequest);
    }

}
