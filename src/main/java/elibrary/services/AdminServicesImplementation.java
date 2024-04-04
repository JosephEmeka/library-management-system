package elibrary.services;

import elibrary.data.model.Admin;
import elibrary.data.model.Book;
import elibrary.data.repository.AdminRepository;
import elibrary.data.repository.BookRepository;
import elibrary.dtos_requests.BookRegisterRequest;
import elibrary.dtos_requests.LoginRequest;
import elibrary.dtos_requests.RegisterRequest;
import elibrary.dtos_response.BookDeleteResponse;
import elibrary.dtos_response.BookRegisterResponse;
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
       Admin admin = adminLoginMap(loginRequest);
        validateLoginRequest(loginRequest, admin);
        return adminLoginResponseMap(admin);
    }

    private void validateLoginRequest(LoginRequest loginRequest, Admin admin) {
        requestCheck(loginRequest.getUsername(), loginRequest.getPassword());
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
    Book newBook = bookRequestMap(newBookRegistrationRequest);
    validateBook(newBook);
    bookRepository.save(newBook);
    return bookRegisterResponseMap(newBook);
    }




    private void validateBook(Book book){
        if ((book.getAuthor() == null) || book.getAuthor().isEmpty() || book.getTitle().isEmpty() || book.getPublisher().isEmpty() || book.getCategory().getDisplayName().isEmpty()) {
            throw new EmptyRegistrationEntryException("Author name cannot be empty.");
        }
        if (book.getAuthor().equals(" ") || book.getTitle().equals(" ") || book.getPublisher().equals(" ") || book.getCategory().getDisplayName().equals(" ")) {
            throw new WhiteSpaceException("User cannot enter white Space");
        }

        Optional<Book> existingBook = bookRepository.findByAuthor(book.getAuthor().toLowerCase().trim());
        if (existingBook.isPresent()) {
            existingBook.get().setAvailable(true);
            throw new BookAlreadyAddedException("User with username " +book.getAuthor() + " already exists.");
        }
    }

    public BookDeleteResponse removeBookByTitleAndAuthor(BookRegisterRequest Book) {
        Book book = bookRequestMap(Book);
        if (book.getTitle() == null || book.getTitle().trim().isEmpty() || book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Title and author cannot be null or empty.");
        }

        Optional<Book> existingBookOptional = bookRepository.findByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if (existingBookOptional.isPresent()) {
            bookRepository.delete(existingBookOptional.get());
        } else {
            throw new BookNotFoundException("Book with title \"" + book.getTitle() + "\" and author \"" + book.getAuthor() + "\" not found.");
        }
        return deleteResponseMap(boo);
    }

   

}

