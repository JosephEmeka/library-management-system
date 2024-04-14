package elibrary.services;

import elibrary.dtos_requests.BookDeleteRequest;
import elibrary.dtos_requests.BookRegisterRequest;
import elibrary.dtos_response.BookDeleteResponse;
import elibrary.dtos_response.BookRegisterResponse;
import org.springframework.stereotype.Service;

@Service
public interface BookServices {
    BookRegisterResponse addBook(BookRegisterRequest newBookRegistrationRequest);
    BookDeleteResponse deleteBook(BookDeleteRequest bookDeleteRequest);
}
