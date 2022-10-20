package ru.otus.spring.service;

import ru.otus.spring.models.Genre;

public interface GenreService {
    Genre create(Genre Genre);

    void showById(long id);

    void showByName(String name);

    void showAll();

    boolean update(long id, String name);

    void deleteById(long id);
}
