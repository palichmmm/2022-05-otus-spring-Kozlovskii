package ru.otus.spring.service;

import ru.otus.spring.models.Author;

import java.util.List;

public interface CRUDModelAuthor {
    long create(Author author);
    Author readById(long id);
    List<Author> readAll();
    boolean update(Author obj);
    boolean deleteById(long id);
}
