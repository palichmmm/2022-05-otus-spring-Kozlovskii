package ru.otus.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, Long> {

    Optional<Author> findById(String id);

    List<Author> findByAuthorName(String name);

    void deleteById(String id);

    boolean existsById(String id);

    boolean existsByAuthorName(String name);
}
