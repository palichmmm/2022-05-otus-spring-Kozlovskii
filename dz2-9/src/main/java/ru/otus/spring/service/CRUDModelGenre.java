package ru.otus.spring.service;

import ru.otus.spring.models.Genre;

import java.util.List;

public interface CRUDModelGenre {
    long create(Genre Genre);
    Genre readById(long id);
    List<Genre> readAll();
    boolean update(Genre obj);
    boolean deleteById(long id);
}
