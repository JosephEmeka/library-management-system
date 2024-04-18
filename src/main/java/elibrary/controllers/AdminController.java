package elibrary.controllers;


import elibrary.dtos_requests.*;
import elibrary.dtos_response.ApiResponse;
import elibrary.services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/elibrary")
public class AdminController {
    @Autowired
    private AdminServices adminServices;


    @PostMapping("/registerAdmin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequest registerRequest) {
        try {
            var result = adminServices.registerAdmin(registerRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                    BAD_REQUEST);
        }
    }

    @PatchMapping("/loginAdmin")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginRequest loginRequest) {
        try {
            var result = adminServices.loginAdmin(loginRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                    BAD_REQUEST);
        }
    }


    @PostMapping("/logoutAdmin")
    public ResponseEntity<?> logoutAdmin(@RequestBody LogOutAdminRequest logOutRequest) {
        try {
            var result = adminServices.logoutAdmin(logOutRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                    BAD_REQUEST);
        }

    }

        @PostMapping("/addBook")
        public ResponseEntity<?> addBook(@RequestBody BookRegisterRequest bookRegisterRequest) {
            try {
                var result = adminServices.addBooks(bookRegisterRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), ACCEPTED);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                        BAD_REQUEST);
            }
        }

        @DeleteMapping ("/removeBook")
        public ResponseEntity<?> deleteBook(@RequestBody BookDeleteRequest bookDeleteRequest) {
            try {
                var result = adminServices.deleteBooks(bookDeleteRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), GONE);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                        BAD_REQUEST);
            }
        }
    }


