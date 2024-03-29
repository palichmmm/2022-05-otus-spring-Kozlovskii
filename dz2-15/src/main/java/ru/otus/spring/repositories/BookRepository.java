package ru.otus.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, Long> {

    Optional<Book> findById(String id);

    List<Book> findByBookName(String name);

    void deleteById(String id);

    boolean existsByAuthor_Id(String id);

    boolean existsByGenre_Id(String id);

    boolean existsById(String id);

    boolean existsByBookName(String name);
}
