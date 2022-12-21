package ru.otus.spring.service;

import ru.otus.spring.models.Author;

import java.util.List;

public interface AuthorService {

    Author findById(String id);

    List<Author> findByName(String name);

    List<Author> findAll();

    Author save(Author author);

    void deleteById(String id);
}
