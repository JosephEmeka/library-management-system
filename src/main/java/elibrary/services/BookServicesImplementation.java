package elibrary.services;

import elibrary.data.model.Book;
import elibrary.data.repository.BookRepository;
import elibrary.dtos_requests.BookDeleteRequest;
import elibrary.dtos_requests.BookRegisterRequest;
import elibrary.dtos_requests.BorrowedBookRegisterRequest;
import elibrary.dtos_response.BookDeleteResponse;
import elibrary.dtos_response.BookRegisterResponse;
import elibrary.dtos_response.BorrowedBookRegisterResponse;
import elibrary.exceptions.BookAlreadyAddedException;
import elibrary.exceptions.BookNotFoundException;
import elibrary.exceptions.EmptyRegistrationEntryException;
import elibrary.exceptions.WhiteSpaceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static elibrary.utils.Mapper.*;
import static elibrary.utils.Mapper.deleteResponseMap;

@Service
public class BookServicesImplementation implements BookServices {

    private  final BookRepository bookRepository;

    @Autowired
    public BookServicesImplementation(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
@Override
    public BookRegisterResponse addBook(BookRegisterRequest newBookRegistrationRequest) {
        Book newBook = bookRequestMap(newBookRegistrationRequest);
        validateBook(newBook);
        if(bookRepository.findBookByIsbn(newBook.getIsbn()) == null) bookRepository.save(newBook);
        else throw new BookAlreadyAddedException("Book already added");
        return bookRegisterResponseMap(newBook);
    }


    public void validateBook(Book book) {
        if (book.getAuthor() == null || book.getAuthor().isEmpty() || book.getTitle().isEmpty() || book.getPublisher().isEmpty() || book.getCategory().getDisplayName().isEmpty()) {
            throw new EmptyRegistrationEntryException("Book details cannot be empty.");
        }
        if (book.getAuthor().equals(" ") || book.getTitle().equals(" ") || book.getPublisher().equals(" ") || book.getCategory().getDisplayName().equals(" ")) {
            throw new WhiteSpaceException("Book details cannot contain white space.");
        }

        if (bookRepository.findByAuthor(book.getAuthor().toLowerCase().trim()).isPresent()) {
            throw new BookAlreadyAddedException("Book with author " + book.getAuthor() + " already exists.");
        }
    }
@Override
    public BookDeleteResponse deleteBook(BookDeleteRequest bookDeleteRequest) {
        Optional<Book> book = bookRepository.findByAuthor(bookDeleteRequest.getAuthor().trim());
        if (book.isEmpty()) {
            throw new BookNotFoundException("Book " + bookDeleteRequest.getAuthor() + " not found");
        }
        bookRepository.delete(book.get());

        return deleteResponseMap(book.get());
    }


}
