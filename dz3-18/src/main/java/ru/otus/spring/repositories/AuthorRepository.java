package ru.otus.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    Optional<Author> findByAuthorName(String name);

    List<Author> findAll();
}
