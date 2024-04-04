package elibrary.controllers;

import elibrary.dtos_requests.BookRegisterRequest;
import elibrary.dtos_requests.LogOutRequest;
import elibrary.dtos_requests.LoginRequest;
import elibrary.dtos_requests.RegisterRequest;
import elibrary.dtos_response.ApiResponse;
import elibrary.services.AdminServicesImplementation;
import elibrary.services.UserServicesImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;


public class ServicesControllers {
    @RestController
    @RequestMapping("/elibrary")
    public static class BlogServicesController {
        private final UserServicesImplementation userServices;
        private AdminServicesImplementation adminServicesImplementation;

        @Autowired
        public BlogServicesController(UserServicesImplementation userServicesImplementation) {
            this.userServices = userServicesImplementation;
        }

        @PostMapping("/register")
        public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
            try {
                var result = userServices.registerUser(registerRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                        BAD_REQUEST);
            }
        }

        @PatchMapping("/login")
        public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
            try {
                var result = userServices.loginUser(loginRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), CONTINUE);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                        BAD_REQUEST);
            }
        }


        @PostMapping("/logout")
        public ResponseEntity<?> logout(@RequestBody LogOutRequest logOutRequest) {
            try {
                var result = userServices.logoutUser(logOutRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                        BAD_REQUEST);
            }

        }


        @PostMapping("/addBook")
        public ResponseEntity<?> addBook(@RequestBody BookRegisterRequest bookRegisterRequest) {
            try {
                var result = adminServicesImplementation.addBooks(bookRegisterRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                        BAD_REQUEST);
            }
        }

        @DeleteMapping ("/removeBook")
        public ResponseEntity<?> deleteBook(@RequestBody BookRegisterRequest bookRegisterRequest) {
            try {
                var result = adminServicesImplementation.removeBookByTitleAndAuthor(bookRegisterRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                        BAD_REQUEST);
            }
        }
    }
}
