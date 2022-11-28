package ru.otus.spring.service;

import ru.otus.spring.models.Book;

import java.util.List;

public interface BookService {
    Book save(Book Book);

    Book findById(long id);

    void findAllCommentsBookById(long id);

    List<Book> findAll();

    void deleteById(long id);

    long count();
}
