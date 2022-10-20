package ru.otus.spring.service;

import ru.otus.spring.models.Book;

public interface BookService {
    Book create(Book Book);

    void showById(long id);

    void showByName(String name);

    void showAllCommentsBookById(long id);

    void showAll();

    boolean update(long id, String name);

    void deleteById(long id);
}
