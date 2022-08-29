package ru.otus.spring.dao;

import ru.otus.spring.models.Author;

import java.util.List;

public interface AuthorDao {
    long count();
    void insert(Author author);
    void update(Author author);
    Author getById(long id);
    List<Author> getAll();
    void deleteById(long id);
}
