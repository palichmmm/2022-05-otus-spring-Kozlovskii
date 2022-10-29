package ru.otus.spring.service;

import ru.otus.spring.models.Author;

public interface AuthorService {
    Author create(Author author);

    void showById(long id);

    void showByName(String name);

    void showAll();

    void update(long id, String name);

    void deleteById(long id);
}
