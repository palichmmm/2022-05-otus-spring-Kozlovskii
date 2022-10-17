package ru.otus.spring.service;

import ru.otus.spring.models.Author;

public interface CRUDModelAuthor {
    Author create(Author author);

    void showById(long id);

    void showByName(String name);

    void showAll();

    boolean update(Author author);

    void deleteById(long id);
}
