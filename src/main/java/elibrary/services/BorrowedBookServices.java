package elibrary.services;

import elibrary.dtos_requests.BorrowedBookDeleteRequest;
import elibrary.dtos_requests.BorrowedBookRegisterRequest;
import elibrary.dtos_response.BorrowedBookDeleteResponse;
import elibrary.dtos_response.BorrowedBookRegisterResponse;
import org.springframework.stereotype.Service;

@Service
public interface BorrowedBookServices {
    BorrowedBookRegisterResponse addBorrowedBook(BorrowedBookRegisterRequest newBookRegistrationRequest);
    BorrowedBookDeleteResponse returnBorrowedBook(BorrowedBookDeleteRequest borrowedBookDeleteRequest);
}
