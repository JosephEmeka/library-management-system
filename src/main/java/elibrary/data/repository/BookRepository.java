package elibrary.data.repository;


import elibrary.data.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findByAuthor(String author);

    Optional<Book> findByTitle(String title);

    Optional<Book> findByTitleAndAuthor(String title, String author);
    Book findBookByIsbn(String isbn);
}
