package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.models.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, Long> {

//    Optional<Author> findById(String id);
//
//    List<Author> findByAuthorName(String name);
//
//    void deleteById(String id);
//
//    boolean existsById(String id);
//
//    boolean existsByAuthorName(String name);
}
