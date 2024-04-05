package elibrary.controllers;


import elibrary.dtos_requests.*;
import elibrary.dtos_response.ApiResponse;
import elibrary.services.AdminServicesImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;


public class AdminController {
    @RestController
    @RequestMapping("/elibrary")
    public static class BlogServicesController {

        private final AdminServicesImplementation adminServicesImplementation;
        @Autowired
        public BlogServicesController( AdminServicesImplementation adminServicesImplementation) {
            this.adminServicesImplementation = adminServicesImplementation;
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
                var result = adminServicesImplementation.removeBookByTitleAndAuthor(bookDeleteRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), GONE);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                        BAD_REQUEST);
            }
        }
    }
}

