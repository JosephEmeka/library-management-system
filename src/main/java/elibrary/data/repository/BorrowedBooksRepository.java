package elibrary.data.repository;

import elibrary.data.model.Book;
import elibrary.data.model.BorrowedBooks;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowedBooksRepository extends MongoRepository<BorrowedBooks, String> {
    Optional <BorrowedBooks> findByTitleAndAuthor(String title, String author);

    Optional<BorrowedBooks> findByTitleAndAuthorAndBorrowerUsername(String title, String author, String borrowerUserName);

    List<BorrowedBooks> findAllByBorrowerUsername(String borrowerUsername);
}
