package elibrary.controllers;

import elibrary.data.model.Book;
import elibrary.data.model.BorrowedBooks;
import elibrary.dtos_requests.*;
import elibrary.dtos_response.ApiResponse;
import elibrary.services.UserServicesImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

    @RestController
    @RequestMapping("/elibrary")
    public class UserServicesControllers{
        private final UserServicesImplementation userServices;

        @Autowired
        public UserServicesControllers(UserServicesImplementation userServices) {
            this.userServices = userServices;
        }


        @PostMapping("/registerUser")
        public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
            try {
                var result = userServices.registerUser(registerRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                        BAD_REQUEST);
            }
        }

        @PatchMapping("/loginUser")
        public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
            try {
                var result = userServices.loginUser(loginRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), CONTINUE);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                        BAD_REQUEST);
            }
        }


        @PostMapping("/logoutUser")
        public ResponseEntity<?> logoutUser(@RequestBody LogOutRequest logOutRequest) {
            try {
                var result = userServices.logoutUser(logOutRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                        BAD_REQUEST);
            }

        }

        @GetMapping("/users/getAllBooks")
        public ResponseEntity<?> findAllBooks() {
            try {
                List<Book> books = userServices.getAllBooks();
                return new ResponseEntity<>(new ApiResponse(true, books), OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
            }
        }

        @GetMapping("/users/getAllBorrowedBooksByUserName")
        public ResponseEntity<?> findAllBorrowedBooksByUser(@RequestBody BorrowedBookRegisterRequest borrowBookRequest) {
            try {
                List<BorrowedBooks> borrowedBooks = userServices.getAllBorrowedBooks(borrowBookRequest);
                return new ResponseEntity<>(new ApiResponse(true, borrowedBooks), OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
            }
        }

        @PostMapping("/borrowBook")
        public ResponseEntity<?> borrowBook(@RequestBody BorrowedBookRegisterRequest borrowBookRequest) {
            try {
                var result = userServices.borrowBooks(borrowBookRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
            }
        }

        @DeleteMapping("/returnBook")
        public ResponseEntity<?> returnBook(@RequestBody BorrowedBookDeleteRequest returnBookRequest) {
            try {
                var result = userServices.deleteBook(returnBookRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
            }
        }
    }

