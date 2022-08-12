package ru.otus.spring.dao;

import ru.otus.spring.models.Author;

import java.util.List;

public interface AuthorDao {
    long count();
    void insert(Author person);
    Author getById(long id);
    List<Author> getAll();
    void deleteById(long id);
}
