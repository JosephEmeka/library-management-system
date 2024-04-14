package elibrary.services;

import elibrary.data.model.Admin;
import elibrary.data.model.Book;
import elibrary.data.model.User;
import elibrary.data.repository.AdminRepository;
import elibrary.data.repository.BookRepository;
import elibrary.dtos_requests.*;
import elibrary.dtos_response.*;
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
   private final BookRepository bookRepository;

    @Autowired
    public AdminServicesImplementation(AdminRepository adminRepository, BookRepository bookRepository) {
        this.adminRepository = adminRepository;
        this.bookRepository = bookRepository;
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
        Admin admin = validateLoginRequest(loginRequest);
        admin.setIsLoggedIn(true);
        adminRepository.save(  admin);
        return adminLoginResponseMap(admin);
    }



    private Admin validateLoginRequest(LoginRequest loginRequest) {
        requestCheck(loginRequest.getUsername(), loginRequest.getPassword());
        Optional<Admin> existingAdminOptional = adminRepository.findByUsername(loginRequest.getUsername());

        if (existingAdminOptional.isPresent()) {

            if (existingAdminOptional.get().getIsLoggedIn()){
                throw new AlreadyLoggedInException("Already logged in");
            }
            if (!Objects.equals(existingAdminOptional.get().getPassword(), loginRequest.getPassword())) {
                throw new WrongPasswordException("Invalid password");
            }
            return existingAdminOptional.get();
        } else {
            throw new NoSuchElementException("User not found.");
        }
    }



    static void requestCheck(String username, String password) {
        if (username == null || username.isEmpty()) {
            throw new EmptyUserNameLoginException("User name cannot be empty.");
        }
        if (password == null || password.isEmpty()) {
            throw new EmptyPasswordLoginException("Password cannot be empty.");
        }
        if (username.equals(" ") || password.equals(" ")) {
            throw new WhiteSpaceException("User cannot enter white Space");
        }
    }

    public BookRegisterResponse addBooks(BookRegisterRequest newBookRegistrationRequest) {
        BookServicesImplementation bookServicesImplementation = new BookServicesImplementation(bookRepository);
        bookServicesImplementation.addBook(newBookRegistrationRequest);
        Optional<Book> newOptionalBook = bookRepository.findByTitleAndAuthor(newBookRegistrationRequest.getTitle(), newBookRegistrationRequest.getAuthor());
        var newBook = newOptionalBook.get();
        return bookRegisterResponseMap(newBook);
    }

    public BookDeleteResponse deleteBooks(BookDeleteRequest newBookDeleteRequest) {
        BookServicesImplementation bookServicesImplementation = new BookServicesImplementation(bookRepository);
        return bookServicesImplementation.deleteBook(newBookDeleteRequest);
    }

    public LogoutAdminResponse logoutAdmin(LogOutAdminRequest newLogOutRequest) {
        Admin admin = adminLoginOutMap(newLogOutRequest);
        validateLogoutRequest(newLogOutRequest, admin);
        return logOutAdminResponseMap(admin);
    }

    private void validateLogoutRequest(LogOutAdminRequest logOutRequest, Admin admin) {
        AdminServicesImplementation.requestCheck(logOutRequest.getUsername(), logOutRequest.getPassword());
        Optional<Admin> existingUserOptional = adminRepository.findByUsername(admin.getUsername());
        if (existingUserOptional.isPresent()) {
            Admin existinAdmin = existingUserOptional.get();
            if (!Objects.equals(existinAdmin.getPassword(), logOutRequest.getPassword())) {
                throw new WrongPasswordException("Account does not exist or Invalid password");
            }
            existinAdmin.setIsLoggedIn(false);
            adminRepository.save(existinAdmin);
        } else {
            throw new NoSuchElementException("User not found");
        }
    }
}

