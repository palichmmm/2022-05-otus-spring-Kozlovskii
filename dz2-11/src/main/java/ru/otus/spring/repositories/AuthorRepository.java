package ru.otus.spring.repositories;

import ru.otus.spring.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Optional<Author> findById(long id);

    Optional<Author> findByName(String name);

    List<Author> findAll();

    Author save(Author author);

    void deleteById(long id);
}
