package ru.otus.spring.service;

import ru.otus.spring.models.Author;

import java.util.List;

public interface AuthorService {
    Author save(Author author);

    Author findById(long id);

    List<Author> findAll();

    void deleteById(long id);
}
