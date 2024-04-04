package elibrary.data.repository;


import elibrary.data.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findByAuthor(String userName);

    Optional<Book> findByTitleAndAuthor(String title, String author);
}
