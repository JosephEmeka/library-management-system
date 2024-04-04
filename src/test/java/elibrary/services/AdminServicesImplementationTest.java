package elibrary.services;

import elibrary.data.model.Admin;
import elibrary.data.repository.AdminRepository;
import elibrary.dtos_requests.BookRegisterRequest;
import elibrary.dtos_requests.LoginRequest;
import elibrary.dtos_requests.RegisterRequest;
import elibrary.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AdminServicesImplementationTest {
    @Autowired
    private AdminRepository adminRepository;
    @BeforeEach
    void setUp() {
        adminRepository.deleteAll();
    }

    @Test
    void testAdminCanBeRegistered(){
        RegisterRequest newUserRegistrationRequest = new RegisterRequest();
        newUserRegistrationRequest.setFirstName("Johnny");
        newUserRegistrationRequest.setLastName("Joe");
        newUserRegistrationRequest.setUserName("google-man");
        newUserRegistrationRequest.setEmail("google-man@gmail.com");
        newUserRegistrationRequest.setPassword("passworded");
        AdminServicesImplementation newAdminServicesImplementation = new AdminServicesImplementation(adminRepository);
        newAdminServicesImplementation.registerAdmin(newUserRegistrationRequest);
        assertEquals(1, adminRepository.count());
    }

    @Test
    void userWithEmptyUserNameCannotBeRegistered_numberOfUsersZero() {
        adminRepository.deleteAll();
        RegisterRequest newUserRegistrationRequest = new RegisterRequest();
        newUserRegistrationRequest.setFirstName("Johnny");
        newUserRegistrationRequest.setLastName("Joe");
        newUserRegistrationRequest.setUserName("");
        newUserRegistrationRequest.setEmail("google-man@gmail.com");
        newUserRegistrationRequest.setPassword("passworded");
        AdminServicesImplementation newAdminServicesImplementation = new AdminServicesImplementation(adminRepository);
        assertThrows(EmptyUserNameRegistrationException.class, ()->newAdminServicesImplementation.registerAdmin(newUserRegistrationRequest));
        assertEquals(0, adminRepository.count());
    }
    @Test
    void userWithEmptyLastNameCannotBeRegistered_numberOfUsersZero() {
        adminRepository.deleteAll();
        RegisterRequest newUserRegistrationRequest = new RegisterRequest();
        newUserRegistrationRequest.setFirstName("Johnny");
        newUserRegistrationRequest.setLastName("");
        newUserRegistrationRequest.setUserName("google-man");
        newUserRegistrationRequest.setEmail("google-man@gmail.com");
        newUserRegistrationRequest.setPassword("passworded");
        AdminServicesImplementation newAdminServicesImplementation = new AdminServicesImplementation(adminRepository);
        assertThrows(EmptyLastNameRegistrationException.class, ()->newAdminServicesImplementation.registerAdmin(newUserRegistrationRequest));
        assertEquals(0, adminRepository.count());
    }

    @Test
    void userWithEmptyFirstNameCannotBeRegistered_numberOfUsersZero() {
        adminRepository.deleteAll();
        RegisterRequest newUserRegistrationRequest = new RegisterRequest();
        newUserRegistrationRequest.setFirstName("");
        newUserRegistrationRequest.setLastName("Joe");
        newUserRegistrationRequest.setUserName("google-man");
        newUserRegistrationRequest.setEmail("google-man@gmail.com");
        newUserRegistrationRequest.setPassword("passworded");
        AdminServicesImplementation newAdminServicesImplementation = new AdminServicesImplementation(adminRepository);
        assertThrows(EmptyFirstNameRegistrationException.class, ()->newAdminServicesImplementation.registerAdmin(newUserRegistrationRequest));
        assertEquals(0, adminRepository.count());
    }

    @Test
    void userWhiteSpaceCannotBeRegistered_numberOfUsersZero() {
        adminRepository.deleteAll();
        RegisterRequest newUserRegistrationRequest = new RegisterRequest();
        newUserRegistrationRequest.setFirstName(" ");
        newUserRegistrationRequest.setLastName(" ");
        newUserRegistrationRequest.setUserName(" ");
        newUserRegistrationRequest.setEmail(" ");
        newUserRegistrationRequest.setPassword(" ");
        AdminServicesImplementation newAdminServicesImplementation = new AdminServicesImplementation(adminRepository);
        assertThrows(WhiteSpaceException.class, ()->newAdminServicesImplementation.registerAdmin(newUserRegistrationRequest));
        assertEquals(0, adminRepository.count());
    }



    @Test
    void twoUserCanBeRegistered_numberOfUsersTwo() {
        adminRepository.deleteAll();

        RegisterRequest newUserRegistrationRequest = new RegisterRequest();
        newUserRegistrationRequest.setFirstName("Johnny");
        newUserRegistrationRequest.setLastName("Joe");
        newUserRegistrationRequest.setUserName("google-man");
        newUserRegistrationRequest.setEmail("google-man@gmail.com");
        newUserRegistrationRequest.setPassword("passworded");

        AdminServicesImplementation newAdminServicesImplementation = new AdminServicesImplementation(adminRepository);
        newAdminServicesImplementation.registerAdmin(newUserRegistrationRequest);

        RegisterRequest secondUserRegistrationRequest = new RegisterRequest();
        secondUserRegistrationRequest.setFirstName("McJohnny");
        secondUserRegistrationRequest.setLastName("Joey");
        secondUserRegistrationRequest.setUserName("twitter-man");
        secondUserRegistrationRequest.setEmail("twitter-man@gmail.com");
        secondUserRegistrationRequest.setPassword("not-passworded");

        newAdminServicesImplementation.registerAdmin(secondUserRegistrationRequest);
        assertEquals(2, adminRepository.count());
    }

    @Test
    void sameUserCannotBeRegisteredTwice() {
        adminRepository.deleteAll();
        RegisterRequest newUserRegistrationRequest = new RegisterRequest();
        newUserRegistrationRequest.setFirstName("Johnny");
        newUserRegistrationRequest.setLastName("Joe");
        newUserRegistrationRequest.setUserName("google-man");
        newUserRegistrationRequest.setEmail("google-man@gmail.com");
        newUserRegistrationRequest.setPassword("passworded");
        AdminServicesImplementation newAdminServicesImplementation = new AdminServicesImplementation(adminRepository);
        newAdminServicesImplementation.registerAdmin(newUserRegistrationRequest);
        assertThrows(DoubleUserRegistrationException.class, () -> newAdminServicesImplementation.registerAdmin(newUserRegistrationRequest));
        assertEquals(1, adminRepository.count());
    }

    @Test
    void testUserCannotLogin_createAccountToLogIn() {
        adminRepository.deleteAll();
        LoginRequest newLoginRequest = new LoginRequest();
        newLoginRequest.setUsername("google-man");
        newLoginRequest.setPassword("PASSWORD");
        AdminServicesImplementation newAdminServicesImplementation = new AdminServicesImplementation(adminRepository);
        Optional<Admin> findUser = adminRepository.findByUsername("google-man");
        assertFalse(findUser.isPresent());
        assertThrows(NoSuchElementException.class, () -> newAdminServicesImplementation.loginAdmin(newLoginRequest));
    }
    @Test
    void testUserCanBeLoggedIn(){
        adminRepository.deleteAll();
        LoginRequest newLoginRequest = new LoginRequest();
        newLoginRequest.setUsername("google-man");
        newLoginRequest.setPassword("PASSWORD");
        AdminServicesImplementation newAdminServicesImplementation = new AdminServicesImplementation(adminRepository);
        Optional<Admin> findUser = adminRepository.findByUsername("google-man");
        assertFalse(findUser.isPresent());
        assertThrows(NoSuchElementException.class, ()->newAdminServicesImplementation.loginAdmin(newLoginRequest));
        RegisterRequest newUserRegistrationRequest = new RegisterRequest();
        newUserRegistrationRequest.setFirstName("Johnny");
        newUserRegistrationRequest.setLastName("Joe");
        newUserRegistrationRequest.setUserName("google-man");
        newUserRegistrationRequest.setEmail("google-man@gmail.com");
        newUserRegistrationRequest.setPassword("PASSWORD");

        newAdminServicesImplementation.registerAdmin(newUserRegistrationRequest);
        assertEquals(1, adminRepository.count());
        newAdminServicesImplementation.loginAdmin(newLoginRequest);
        assertTrue(adminRepository.findByUsername("google-man").get().getIsLoggedIn());
    }
    @Test
    void testUserCanBeLoggedInWithWrongUserName(){
        adminRepository.deleteAll();
        LoginRequest newLoginRequest = new LoginRequest();
        newLoginRequest.setUsername("google-man");
        newLoginRequest.setPassword("PASSWORD");
        AdminServicesImplementation newAdminServicesImplementation = new AdminServicesImplementation(adminRepository);
        RegisterRequest newUserRegistrationRequest = new RegisterRequest();
        newUserRegistrationRequest.setFirstName("Johnny");
        newUserRegistrationRequest.setLastName("Joe");
        newUserRegistrationRequest.setUserName("google-man");
        newUserRegistrationRequest.setEmail("google-man@gmail.com");
        newUserRegistrationRequest.setPassword("PASSWORD");
        Optional<Admin> findUser = adminRepository.findByUsername("geogle-man");
        assertFalse(findUser.isPresent());
        assertThrows(NoSuchElementException.class, ()-> newAdminServicesImplementation.loginAdmin(newLoginRequest));
    }
    @Test
    void testUserCanBeLoggedInWithWrongPassword(){
       adminRepository.deleteAll();
        AdminServicesImplementation newAdminServicesImplementation = getAdminServicesImplementation(adminRepository);

        LoginRequest newLoginRequest = new LoginRequest();
        newLoginRequest.setUsername("google-man");
        newLoginRequest.setPassword("PASSWORDED");
        Optional<Admin> findUser = adminRepository.findByUsername("google-man");
        assertTrue(findUser.isPresent());
        assertThrows(WrongPasswordException.class, ()->newAdminServicesImplementation.loginAdmin(newLoginRequest));
    }

    @Test
    void testUserCanBeLoggedInWithEmptyUserName(){
       adminRepository.deleteAll();
        AdminServicesImplementation newAdminServicesImplementation = getAdminServicesImplementation(adminRepository);

        LoginRequest newLoginRequest = new LoginRequest();
        newLoginRequest.setUsername("");
        newLoginRequest.setPassword("PASSWORD");
        Optional<Admin> findUser = adminRepository.findByUsername("google-man");
        assertTrue(findUser.isPresent());
        assertThrows(EmptyUserNameLoginException.class, ()->newAdminServicesImplementation.loginAdmin(newLoginRequest));
    }

    @Test
    void testUserCanBeLoggedInWithWhiteSpaceUserName(){
        adminRepository.deleteAll();
        AdminServicesImplementation newAdminServicesImplementation = getAdminServicesImplementation(adminRepository);

        LoginRequest newLoginRequest = new LoginRequest();
        newLoginRequest.setUsername(" ");
        newLoginRequest.setPassword("PASSWORD");
        Optional<Admin> findUser = adminRepository.findByUsername("google-man");
        assertTrue(findUser.isPresent());
        assertThrows(WhiteSpaceException.class, ()->newAdminServicesImplementation.loginAdmin(newLoginRequest));
    }

    @Test
    void testUserCanBeLoggedInWithEmptyPassword(){
        adminRepository.deleteAll();
        AdminServicesImplementation newAdminServicesImplementation = getAdminServicesImplementation(adminRepository);

        LoginRequest newLoginRequest = new LoginRequest();
        newLoginRequest.setUsername("google-man");
        newLoginRequest.setPassword("");
        Optional<Admin> findUser = adminRepository.findByUsername("google-man");
        assertTrue(findUser.isPresent());
        assertThrows(EmptyPasswordLoginException.class, ()->newAdminServicesImplementation.loginAdmin(newLoginRequest));
    }

    @Test
    void testUserCanBeLoggedInWithWhiteSpacePassword(){
        adminRepository.deleteAll();
        AdminServicesImplementation newAdminServicesImplementation = getAdminServicesImplementation(adminRepository);

        LoginRequest newLoginRequest = new LoginRequest();
        newLoginRequest.setUsername("google-man");
        newLoginRequest.setPassword(" ");
        Optional<Admin> findUser = adminRepository.findByUsername("google-man");
        assertTrue(findUser.isPresent());
        assertThrows(WhiteSpaceException.class, ()->newAdminServicesImplementation.loginAdmin(newLoginRequest));
    }

    private static AdminServicesImplementation getAdminServicesImplementation(AdminRepository adminRepository) {
        AdminServicesImplementation newAdminServicesImplementation = new AdminServicesImplementation(adminRepository);
        RegisterRequest newAdminRegistrationRequest = new RegisterRequest();
        newAdminRegistrationRequest.setFirstName("Johnny");
        newAdminRegistrationRequest.setLastName("Joe");
        newAdminRegistrationRequest.setUserName("google-man");
        newAdminRegistrationRequest.setEmail("google-man@gmail.com");
        newAdminRegistrationRequest.setPassword("PASSWORD");
        newAdminServicesImplementation.registerAdmin(newAdminRegistrationRequest);
        return newAdminServicesImplementation;
    }

    @Test
    void testThatAdminCanUploadBooks(){
        AdminServicesImplementation newAdminServicesImplementation = new AdminServicesImplementation(adminRepository);
       var newBookRegistrationRequest = new BookRegisterRequest();
       newBookRegistrationRequest.setAuthor("Author");
       newBookRegistrationRequest.setTitle("Title");

        newAdminServicesImplementation.uploadBooks();
    }

}