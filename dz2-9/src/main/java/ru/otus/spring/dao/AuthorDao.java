package ru.otus.spring.dao;

import ru.otus.spring.models.Author;

import java.util.List;

public interface AuthorDao {
    int count();
    void insert(Author person);
    Author getById(int id);
    List<Author> getAll();
    void deleteById(int id);
}
