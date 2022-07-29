package ru.otus.spring.dao;

import ru.otus.spring.models.Book;

import java.util.List;

public interface BookDao {
    int count();
    void insert(Book book);
    Book getById(int id);
    List<Book> getAll();
    void deleteById(int id);
}
