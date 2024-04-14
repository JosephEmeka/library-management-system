package elibrary.controllers;


import elibrary.dtos_requests.*;
import elibrary.dtos_response.ApiResponse;
import elibrary.services.AdminServicesImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/elibrary")
public class AdminController {
        private final AdminServicesImplementation adminServicesImplementation;
        @Autowired
        public  AdminController(AdminServicesImplementation adminServicesImplementation) {
            this.adminServicesImplementation = adminServicesImplementation;
        }

    @PostMapping("/registerAdmin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequest registerRequest) {
        try {
            var result = adminServicesImplementation.registerAdmin(registerRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                    BAD_REQUEST);
        }
    }

    @PatchMapping("/loginAdmin")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginRequest loginRequest) {
        try {
            var result = adminServicesImplementation.loginAdmin(loginRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CONTINUE);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                    BAD_REQUEST);
        }
    }


    @PostMapping("/logoutAdmin")
    public ResponseEntity<?> logoutAdmin(@RequestBody LogOutAdminRequest logOutRequest) {
        try {
            var result = adminServicesImplementation.logoutAdmin(logOutRequest);
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
                return new ResponseEntity<>(new ApiResponse(true, result), ACCEPTED);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                        BAD_REQUEST);
            }
        }

        @DeleteMapping ("/removeBook")
        public ResponseEntity<?> deleteBook(@RequestBody BookDeleteRequest bookDeleteRequest) {
            try {
                var result = adminServicesImplementation.deleteBooks(bookDeleteRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), GONE);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                        BAD_REQUEST);
            }
        }
    }


