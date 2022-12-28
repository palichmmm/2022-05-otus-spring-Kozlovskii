package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.models.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, Long> {

//    Optional<Book> findById(String id);
//
//    List<Book> findByBookName(String name);
//
//    void deleteById(String id);
//
//    boolean existsByAuthor_Id(String id);
//
//    boolean existsByGenre_Id(String id);
//
//    boolean existsById(String id);
//
//    boolean existsByBookName(String name);
}
