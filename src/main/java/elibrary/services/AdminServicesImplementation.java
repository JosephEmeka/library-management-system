package elibrary.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import elibrary.data.model.Admin;
import elibrary.data.model.Book;
import elibrary.data.repository.AdminRepository;
import elibrary.data.repository.BookRepository;
import elibrary.dtos_requests.*;
import elibrary.dtos_response.*;
import elibrary.exceptions.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static elibrary.utils.Mapper.*;

@Service
@AllArgsConstructor
public class AdminServicesImplementation implements AdminServices {

    private final AdminRepository adminRepository;
    private final BookRepository bookRepository;
    private final Cloudinary cloudinary;
    private final ModelMapper modelMapper;


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
@Override
    public BookRegisterResponse addBooks(BookRegisterRequest newBookRegistrationRequest) {
        BookServicesImplementation bookServicesImplementation = new BookServicesImplementation(bookRepository);
        bookServicesImplementation.addBook(newBookRegistrationRequest);
        Optional<Book> newOptionalBook = bookRepository.findByTitleAndAuthor(newBookRegistrationRequest.getTitle(), newBookRegistrationRequest.getAuthor());
        var newBook = newOptionalBook.get();
        return bookRegisterResponseMap(newBook);
    }
@Override
    public BookDeleteResponse deleteBooks(BookDeleteRequest newBookDeleteRequest) {
        BookServicesImplementation bookServicesImplementation = new BookServicesImplementation(bookRepository);
        return bookServicesImplementation.deleteBook(newBookDeleteRequest);
    }
@Override
    public LogoutAdminResponse logoutAdmin(LogOutAdminRequest newLogOutRequest) {
        Admin admin = adminLoginOutMap(newLogOutRequest);
        validateLogoutRequest(newLogOutRequest, admin);
        return logOutAdminResponseMap(admin);
    }

    @Override
    public UploadBookResponse upload(UploadBookRequest uploadBookRequest) {
      Admin admin = adminRepository.findByEmail(uploadBookRequest.getAdminEmail());
      Optional<Book> book = bookRepository.findByTitleAndAuthor(uploadBookRequest.getTitle(), uploadBookRequest.getAuthor());
        if (book.isEmpty()) {
            try {
                Uploader uploader = cloudinary.uploader();
                Map<?, ?> response = uploader.upload(uploadBookRequest.getMediaFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                String url = response.get("url").toString();
                Book newBook = modelMapper.map(uploadBookRequest, Book.class);
                newBook.setBookUrl(url);
                newBook.setUploader(admin.getAdminId());
                newBook = bookRepository.save(newBook);
                return modelMapper.map(newBook, UploadBookResponse.class);
            } catch (IOException exception) {
                throw new BookUploadFailedException("Book upload failed");
            }
        }
        else{
            throw new BookAlreadyAddedException("Book already added");
        }
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

