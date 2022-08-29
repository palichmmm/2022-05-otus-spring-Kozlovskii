package ru.otus.spring.service;

import ru.otus.spring.models.Book;

import java.util.List;

public interface CRUDModelBook {
    long create(Book Book);
    Book readById(long id);
    List<Book> readAll();
    boolean update(Book obj);
    boolean deleteById(long id);
}
