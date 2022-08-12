package ru.otus.spring.dao;

import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;

import java.util.List;

public interface BookDao {
    long count();
    void insert(Book book);
    void update(Book book);
    Book getById(long id);
    List<Book> getAll();
    void deleteById(long id);
}
