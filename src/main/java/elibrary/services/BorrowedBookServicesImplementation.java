package elibrary.services;


import elibrary.data.model.Book;
import elibrary.data.model.BorrowedBooks;
import elibrary.data.repository.BookRepository;
import elibrary.data.repository.BorrowedBooksRepository;
import elibrary.dtos_requests.BorrowedBookDeleteRequest;
import elibrary.dtos_requests.BorrowedBookRegisterRequest;
import elibrary.dtos_response.BorrowedBookDeleteResponse;
import elibrary.dtos_response.BorrowedBookRegisterResponse;
import elibrary.exceptions.BookAlreadyAddedException;
import elibrary.exceptions.EmptyRegistrationEntryException;
import elibrary.exceptions.WhiteSpaceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

import static elibrary.utils.Mapper.*;


@Service
public class BorrowedBookServicesImplementation implements BorrowedBookServices {
    private final BorrowedBooksRepository borrowedBooksRepository;
    private final BookRepository bookRepository;
@Autowired
    public BorrowedBookServicesImplementation(BorrowedBooksRepository borrowedBooksRepository, BookRepository bookRepository){
        this.borrowedBooksRepository = borrowedBooksRepository;
        this.bookRepository = bookRepository;
    }


    public BorrowedBookRegisterResponse addBorrowedBook(BorrowedBookRegisterRequest newBookRegistrationRequest) {
        BorrowedBooks bookToBeBorrowed = borrowedBookRequestMap(newBookRegistrationRequest);
        validateBorrowedBook(bookToBeBorrowed);
        Optional<Book> bookToBeBorrowedFromBookRepository = bookRepository.findByTitleAndAuthor(bookToBeBorrowed.getTitle().toLowerCase().trim(), bookToBeBorrowed.getAuthor().toLowerCase().trim());
        if (bookToBeBorrowedFromBookRepository.isPresent()) {
           Book newBookToBeBorrowedFromBookRep =  bookToBeBorrowedFromBookRepository.get();
            if(borrowedBooksRepository.findByTitleAndAuthor(bookToBeBorrowed.getTitle(), bookToBeBorrowed.getAuthor()).isPresent())
                throw new BookAlreadyAddedException("Book already borrowed");
            else {
                newBookToBeBorrowedFromBookRep.setAvailable(false);
                bookRepository.save(newBookToBeBorrowedFromBookRep);
                borrowedBooksRepository.save(bookToBeBorrowed);
                return borrowedBookRegisterResponseMap(bookToBeBorrowed);
            }
        }
        else {
            throw new NoSuchElementException("Book not found");
        }
    }

    public void validateBorrowedBook(BorrowedBooks borrowedBook) {
        if (borrowedBook.getAuthor() == null || borrowedBook.getAuthor().isEmpty() || borrowedBook.getTitle().isEmpty() || borrowedBook.getBorrowerUsername().isEmpty()) {
            throw new EmptyRegistrationEntryException("Book details cannot be empty.");
        }
        if (borrowedBook.getAuthor().equals(" ") || borrowedBook.getTitle().equals(" ") || borrowedBook.getBorrowerUsername().equals(" ")) {
            throw new WhiteSpaceException("Book details cannot contain white space.");
        }
    }


    public BorrowedBookDeleteResponse returnBorrowedBook(BorrowedBookDeleteRequest borrowedBookDeleteRequest) {
        if (borrowedBookDeleteRequest.getTitle() == null || borrowedBookDeleteRequest.getTitle().isEmpty() || borrowedBookDeleteRequest.getAuthor() == null || borrowedBookDeleteRequest.getAuthor().isEmpty() || borrowedBookDeleteRequest.getBorrowerUserName() == null || borrowedBookDeleteRequest.getBorrowerUserName().isEmpty()) {
            throw new EmptyRegistrationEntryException("Book details cannot be empty.");
        }
        if (borrowedBookDeleteRequest.getTitle().trim().isEmpty() || borrowedBookDeleteRequest.getAuthor().trim().isEmpty() || borrowedBookDeleteRequest.getBorrowerUserName().trim().isEmpty()) {
            throw new WhiteSpaceException("Book details cannot contain white space.");
        }

        Optional<BorrowedBooks> bookToBeReturnedToBookRepository = borrowedBooksRepository.findByTitleAndAuthorAndBorrowerUsername(borrowedBookDeleteRequest.getTitle().toLowerCase().trim(), borrowedBookDeleteRequest.getAuthor().toLowerCase().trim(), borrowedBookDeleteRequest.getBorrowerUserName().toLowerCase().trim());
        if (bookToBeReturnedToBookRepository.isPresent()) {
            borrowedBooksRepository.deleteById(bookToBeReturnedToBookRepository.get().getBookId());
            Optional<Book> borrowedBook = bookRepository.findByTitleAndAuthor(borrowedBookDeleteRequest.getTitle(), borrowedBookDeleteRequest.getAuthor());
            if (borrowedBook.isPresent()) {
                borrowedBook.get().setAvailable(true);
                bookRepository.save(borrowedBook.get());
                return borrowedBookDeleteResponseMap(bookToBeReturnedToBookRepository.get());
            } else {
                throw new NoSuchElementException("Book is not borrowed by the specified user.");
            }
        } else {
            throw new NoSuchElementException("Book not found in the library.");
        }
    }


}
