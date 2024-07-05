package elibrary.services;

import elibrary.data.model.Admin;
import elibrary.data.repository.AdminRepository;
import elibrary.data.repository.BookRepository;
import elibrary.dtos_requests.*;
import elibrary.dtos_response.UploadBookResponse;
import elibrary.enums.Category;
import elibrary.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Optional;

import static elibrary.utils.TestUtils.TEST_BOOK_COVER_PAGE_LOCATION;
import static elibrary.utils.TestUtils.buildUploadMediaRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class AdminServicesImplementationTest {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AdminServices adminServices;
//    @BeforeEach
//    void setUp() {
//        adminRepository.deleteAll();
//        bookRepository.deleteAll();
//    }

    @Test
    void testAdminCanBeRegistered(){
        RegisterRequest newUserRegistrationRequest = new RegisterRequest();
        newUserRegistrationRequest.setFirstName("Johnny");
        newUserRegistrationRequest.setLastName("Joe");
        newUserRegistrationRequest.setUserName("google-man");
        newUserRegistrationRequest.setEmail("google-man@gmail.com");
        newUserRegistrationRequest.setPassword("passworded");
        adminServices.registerAdmin(newUserRegistrationRequest);
        assertEquals(1, adminRepository.count());
    }

    @Test
    void testAdminWithEmptyUserNameCannotBeRegistered_numberOfAdminZero() {
        RegisterRequest newUserRegistrationRequest = new RegisterRequest();
        newUserRegistrationRequest.setFirstName("Johnny");
        newUserRegistrationRequest.setLastName("Joe");
        newUserRegistrationRequest.setUserName("");
        newUserRegistrationRequest.setEmail("google-man@gmail.com");
        newUserRegistrationRequest.setPassword("passworded");
        assertThrows(EmptyUserNameRegistrationException.class, ()->adminServices.registerAdmin(newUserRegistrationRequest));
        assertEquals(1, adminRepository.count());
    }
    @Test
    void testAdminWithEmptyLastNameCannotBeRegistered_numberOfUsersZero() {
        RegisterRequest newUserRegistrationRequest = new RegisterRequest();
        newUserRegistrationRequest.setFirstName("Johnny");
        newUserRegistrationRequest.setLastName("");
        newUserRegistrationRequest.setUserName("google-man");
        newUserRegistrationRequest.setEmail("google-man@gmail.com");
        newUserRegistrationRequest.setPassword("passworded");;
        assertThrows(EmptyLastNameRegistrationException.class, ()->adminServices.registerAdmin(newUserRegistrationRequest));
        assertEquals(1, adminRepository.count());
    }

    @Test
    void testAdminWithEmptyFirstNameCannotBeRegistered_numberOfUsersZero() {
        RegisterRequest newUserRegistrationRequest = new RegisterRequest();
        newUserRegistrationRequest.setFirstName("");
        newUserRegistrationRequest.setLastName("Joe");
        newUserRegistrationRequest.setUserName("google-man");
        newUserRegistrationRequest.setEmail("google-man@gmail.com");
        newUserRegistrationRequest.setPassword("passworded");
        assertThrows(EmptyFirstNameRegistrationException.class, ()->adminServices.registerAdmin(newUserRegistrationRequest));
        assertEquals(1, adminRepository.count());
    }

    @Test
    void testAdminWhiteSpaceCannotBeRegistered_numberOfUsersZero() {
        RegisterRequest newUserRegistrationRequest = new RegisterRequest();
        newUserRegistrationRequest.setFirstName(" ");
        newUserRegistrationRequest.setLastName(" ");
        newUserRegistrationRequest.setUserName(" ");
        newUserRegistrationRequest.setEmail(" ");
        newUserRegistrationRequest.setPassword(" ");
        assertThrows(WhiteSpaceException.class, ()->adminServices.registerAdmin(newUserRegistrationRequest));
        assertEquals(1, adminRepository.count());
    }



    @Test
    void twoAdminCanBeRegistered_numberOfUsersTwo() {
        RegisterRequest newUserRegistrationRequest = new RegisterRequest();
        newUserRegistrationRequest.setFirstName("Johnny");
        newUserRegistrationRequest.setLastName("Joe");
        newUserRegistrationRequest.setUserName("google-man");
        newUserRegistrationRequest.setEmail("google-man@gmail.com");
        newUserRegistrationRequest.setPassword("passworded");
        adminServices.registerAdmin(newUserRegistrationRequest);

        RegisterRequest secondUserRegistrationRequest = new RegisterRequest();
        secondUserRegistrationRequest.setFirstName("McJohnny");
        secondUserRegistrationRequest.setLastName("Joey");
        secondUserRegistrationRequest.setUserName("twitter-man");
        secondUserRegistrationRequest.setEmail("twitter-man@gmail.com");
        secondUserRegistrationRequest.setPassword("not-passworded");

        adminServices.registerAdmin(secondUserRegistrationRequest);
        assertEquals(2, adminRepository.count());
    }

    @Test
    void sameAdminCannotBeRegisteredTwice() {
        RegisterRequest newUserRegistrationRequest = new RegisterRequest();
        newUserRegistrationRequest.setFirstName("Johnny");
        newUserRegistrationRequest.setLastName("Joe");
        newUserRegistrationRequest.setUserName("google-man");
        newUserRegistrationRequest.setEmail("google-man@gmail.com");
        newUserRegistrationRequest.setPassword("passworded");
        adminServices.registerAdmin(newUserRegistrationRequest);
        assertThrows(DoubleUserRegistrationException.class, () -> adminServices.registerAdmin(newUserRegistrationRequest));
        assertEquals(1, adminRepository.count());
    }

    @Test
    void testAdminCannotLoginWithoutRegistration_ThrowsException() {
        adminRepository.deleteAll();
        LoginRequest newLoginRequest = new LoginRequest();
        newLoginRequest.setUsername("google-man");
        newLoginRequest.setPassword("PASSWORD");
        Optional<Admin> findUser = adminRepository.findByUsername("google-man");
        assertFalse(findUser.isPresent());
        assertThrows(NoSuchElementException.class, () -> adminServices.loginAdmin(newLoginRequest));
    }
    @Test
    void testAdminCanBeLoggedIn_adminTriesToLogInAgain_ThrowsException(){
        adminRepository.deleteAll();
        RegisterRequest newUserRegistrationRequest = new RegisterRequest();
        newUserRegistrationRequest.setFirstName("Johnny");
        newUserRegistrationRequest.setLastName("Joe");
        newUserRegistrationRequest.setUserName("google-man");
        newUserRegistrationRequest.setEmail("google-man@gmail.com");
        newUserRegistrationRequest.setPassword("PASSWORD");
        LoginRequest newLoginRequest = new LoginRequest();
        newLoginRequest.setUsername("google-man");
        newLoginRequest.setPassword("PASSWORD");
        adminServices.registerAdmin(newUserRegistrationRequest);
        Optional<Admin> findUser = adminRepository.findByUsername(newLoginRequest.getUsername());
        assertTrue(findUser.isPresent());
        assertFalse(findUser.get().getIsLoggedIn());
        adminServices.loginAdmin(newLoginRequest);
        findUser = adminRepository.findByUsername(newLoginRequest.getUsername());
        assertTrue(findUser.get().getIsLoggedIn());
        assertEquals(1, adminRepository.count());
        assertThrows(AlreadyLoggedInException.class,()-> adminServices.loginAdmin(newLoginRequest));
        assertTrue(adminRepository.findByUsername("google-man").get().getIsLoggedIn());
    }
    @Test
    void testAdminCannotBeLoggedInWithWrongUserName(){
        LoginRequest newLoginRequest = new LoginRequest();
        newLoginRequest.setUsername("google-man");
        newLoginRequest.setPassword("PASSWORD");
        RegisterRequest newUserRegistrationRequest = new RegisterRequest();
        newUserRegistrationRequest.setFirstName("Johnny");
        newUserRegistrationRequest.setLastName("Joe");
        newUserRegistrationRequest.setUserName("google-man");
        newUserRegistrationRequest.setEmail("google-man@gmail.com");
        newUserRegistrationRequest.setPassword("PASSWORD");
        Optional<Admin> findUser = adminRepository.findByUsername("geogle-man");
        assertFalse(findUser.isPresent());
        assertThrows(NoSuchElementException.class, ()-> adminServices.loginAdmin(newLoginRequest));
    }
    @Test
    void testUserCannotBeLoggedInWithWrongPassword(){
        RegisterRequest newAdminRegistrationRequest = new RegisterRequest();
        newAdminRegistrationRequest.setFirstName("Johnny");
        newAdminRegistrationRequest.setLastName("Joe");
        newAdminRegistrationRequest.setUserName("google-man");
        newAdminRegistrationRequest.setEmail("google-man@gmail.com");
        newAdminRegistrationRequest.setPassword("PASSWORD");
        adminServices.registerAdmin(newAdminRegistrationRequest);
        LoginRequest newLoginRequest = new LoginRequest();
        newLoginRequest.setUsername("google-man");
        newLoginRequest.setPassword("PASSWORDED");
        Optional<Admin> findUser = adminRepository.findByUsername("google-man");
        assertTrue(findUser.isPresent());
        assertThrows(WrongPasswordException.class, ()->adminServices.loginAdmin(newLoginRequest));
    }

    @Test
    void testUserCannotBeLoggedInWithEmptyUserName(){
        RegisterRequest newAdminRegistrationRequest = new RegisterRequest();
        newAdminRegistrationRequest.setFirstName("Johnny");
        newAdminRegistrationRequest.setLastName("Joe");
        newAdminRegistrationRequest.setUserName("google-man");
        newAdminRegistrationRequest.setEmail("google-man@gmail.com");
        newAdminRegistrationRequest.setPassword("PASSWORD");
        adminServices.registerAdmin(newAdminRegistrationRequest);
        LoginRequest newLoginRequest = new LoginRequest();
        newLoginRequest.setUsername("");
        newLoginRequest.setPassword("PASSWORD");
        Optional<Admin> findUser = adminRepository.findByUsername("google-man");
        assertTrue(findUser.isPresent());
        assertThrows(EmptyUserNameLoginException.class, ()-> adminServices.loginAdmin(newLoginRequest));
    }

    @Test
    void testUserCannotBeLoggedInWithWhiteSpaceUserName(){
        RegisterRequest newAdminRegistrationRequest = new RegisterRequest();
        newAdminRegistrationRequest.setFirstName("Johnny");
        newAdminRegistrationRequest.setLastName("Joe");
        newAdminRegistrationRequest.setUserName("google-man");
        newAdminRegistrationRequest.setEmail("google-man@gmail.com");
        newAdminRegistrationRequest.setPassword("PASSWORD");
        adminServices.registerAdmin(newAdminRegistrationRequest);

        LoginRequest newLoginRequest = new LoginRequest();
        newLoginRequest.setUsername(" ");
        newLoginRequest.setPassword("PASSWORD");
        Optional<Admin> findUser = adminRepository.findByUsername("google-man");
        assertTrue(findUser.isPresent());
        assertThrows(WhiteSpaceException.class, ()->adminServices.loginAdmin(newLoginRequest));
    }

    @Test
    void testUserCannotBeLoggedInWithEmptyPassword(){
        RegisterRequest newAdminRegistrationRequest = new RegisterRequest();
        newAdminRegistrationRequest.setFirstName("Johnny");
        newAdminRegistrationRequest.setLastName("Joe");
        newAdminRegistrationRequest.setUserName("google-man");
        newAdminRegistrationRequest.setEmail("google-man@gmail.com");
        newAdminRegistrationRequest.setPassword("PASSWORD");
        adminServices.registerAdmin(newAdminRegistrationRequest);
        LoginRequest newLoginRequest = new LoginRequest();
        newLoginRequest.setUsername("google-man");
        newLoginRequest.setPassword("");
        Optional<Admin> findUser = adminRepository.findByUsername("google-man");
        assertTrue(findUser.isPresent());
        assertThrows(EmptyPasswordLoginException.class, ()-> adminServices.loginAdmin(newLoginRequest));
    }

    @Test
    void testUserCannotBeLoggedInWithWhiteSpacePassword(){
        RegisterRequest newAdminRegistrationRequest = new RegisterRequest();
        newAdminRegistrationRequest.setFirstName("Johnny");
        newAdminRegistrationRequest.setLastName("Joe");
        newAdminRegistrationRequest.setUserName("google-man");
        newAdminRegistrationRequest.setEmail("google-man@gmail.com");
        newAdminRegistrationRequest.setPassword("PASSWORD");
        adminServices.registerAdmin(newAdminRegistrationRequest);
        LoginRequest newLoginRequest = new LoginRequest();
        newLoginRequest.setUsername("google-man");
        newLoginRequest.setPassword(" ");
        Optional<Admin> findUser = adminRepository.findByUsername("google-man");
        assertTrue(findUser.isPresent());
        assertThrows(WhiteSpaceException.class, ()->adminServices.loginAdmin(newLoginRequest));
    }





    @Test
    void testThatAdminCanAddBooks(){
        var newBookRegistrationRequest = new BookRegisterRequest();
        newBookRegistrationRequest.setAuthor("Author");
        newBookRegistrationRequest.setTitle("Title");
        newBookRegistrationRequest.setPublisher("johnson & johnson");
        newBookRegistrationRequest.setIsbn("23bnn432");
        newBookRegistrationRequest.setCategory(Category.CHILDREN);
        adminServices.addBooks(newBookRegistrationRequest);
        assertEquals(1, bookRepository.count());
    }

    @Test
    void testThatAdminCannotAddSameBookTwice(){
        var newBookRegistrationRequest = new BookRegisterRequest();
        newBookRegistrationRequest.setAuthor("Author");
        newBookRegistrationRequest.setTitle("Title");
        newBookRegistrationRequest.setPublisher("johnson & johnson");
        newBookRegistrationRequest.setIsbn("23bnn432");
        newBookRegistrationRequest.setCategory(Category.MYSTERY);
        adminServices.addBooks(newBookRegistrationRequest);
        assertEquals(1, bookRepository.count());
        assertThrows(BookAlreadyAddedException.class, ()-> adminServices.addBooks(newBookRegistrationRequest));
    }

    @Test
    void testThatAdminCanAddTwoBooks(){
        var newBookRegistrationRequest = new BookRegisterRequest();
        newBookRegistrationRequest.setAuthor("Author");
        newBookRegistrationRequest.setTitle("Title");
        newBookRegistrationRequest.setPublisher("johnson & johnson");
        newBookRegistrationRequest.setIsbn("23bnn432");
        newBookRegistrationRequest.setCategory(Category.MYSTERY);
        adminServices.addBooks(newBookRegistrationRequest);
        var secondBookRegistrationRequest = new BookRegisterRequest();
        secondBookRegistrationRequest.setAuthor("Sam");
        secondBookRegistrationRequest.setTitle("The Little Mermaid");
        secondBookRegistrationRequest.setPublisher("johnson & johnson");
        secondBookRegistrationRequest.setIsbn("23bnR432");
        secondBookRegistrationRequest.setCategory(Category.SCIENCE_FICTION);
        adminServices.addBooks(secondBookRegistrationRequest);
        assertEquals(2, bookRepository.count());
    }

    @Test
    void testThatAdminCannotAddBooks_loginToAddBook(){
        var newBookRegistrationRequest = new BookRegisterRequest();
        newBookRegistrationRequest.setAuthor("Author");
        newBookRegistrationRequest.setTitle("Title");
        newBookRegistrationRequest.setPublisher("johnson & johnson");
        newBookRegistrationRequest.setIsbn("23bnn432");
        newBookRegistrationRequest.setCategory(Category.MYSTERY);
        adminServices.addBooks(newBookRegistrationRequest);
        var secondBookRegistrationRequest = new BookRegisterRequest();
        secondBookRegistrationRequest.setAuthor("Sam");
        secondBookRegistrationRequest.setTitle("The Little Mermaid");
        secondBookRegistrationRequest.setPublisher("johnson & johnson");
        secondBookRegistrationRequest.setIsbn("23bon432");
        secondBookRegistrationRequest.setCategory(Category.SCIENCE_FICTION);
        adminServices.addBooks(secondBookRegistrationRequest);
        assertEquals(2, bookRepository.count());
    }

    @Test
    void testAdminCanDeleteBook(){
        var newBookRegistrationRequest = new BookRegisterRequest();
        newBookRegistrationRequest.setAuthor("Author");
        newBookRegistrationRequest.setTitle("Title");
        newBookRegistrationRequest.setPublisher("johnson & johnson");
        newBookRegistrationRequest.setIsbn("23bnn432");
        newBookRegistrationRequest.setCategory(Category.MYSTERY);
        adminServices.addBooks(newBookRegistrationRequest);
        var secondBookRegistrationRequest = new BookRegisterRequest();
        secondBookRegistrationRequest.setAuthor("Sam");
        secondBookRegistrationRequest.setTitle("The Little Mermaid");
        secondBookRegistrationRequest.setPublisher("johnson & johnson");
        secondBookRegistrationRequest.setIsbn("23bnR432");
        secondBookRegistrationRequest.setCategory(Category.SCIENCE_FICTION);
        adminServices.addBooks(secondBookRegistrationRequest);
        assertEquals(2, bookRepository.count());
        var newBookDeleteRequest = new BookDeleteRequest();
        newBookDeleteRequest.setAuthor("Author");
        newBookDeleteRequest.setTitle("Title");
        newBookDeleteRequest.setIsbn("23bnn432");
        newBookDeleteRequest.setBookCategory(Category.MYSTERY);
        adminServices.deleteBooks(newBookDeleteRequest);
        assertEquals(1, bookRepository.count());
    }

    @Test
    void testAdminCanUploadBookForDisplay(){
               Path path = Paths.get(TEST_BOOK_COVER_PAGE_LOCATION);
        try(var inputStream = Files.newInputStream(path)){
            UploadBookRequest request  = buildUploadMediaRequest(inputStream);
            UploadBookResponse response = adminServices.upload(request);
            assertThat(response).isNotNull();
            assertThat(response.getUrl()).isNotNull();
        }
        catch (IOException exception){
            assertThat(exception).isNotNull();
        }

    }

    @Test
    void testSameBookCannotBeUploadedAddedTwice(){}
}