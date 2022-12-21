package ru.otus.spring.service;

import ru.otus.spring.models.Book;

import java.util.List;

public interface BookService {
    Book findById(String id);

    List<Book> findByName(String name);

    List<Book> findAll();

    Book save(Book book);

    void deleteById(String id);
}
