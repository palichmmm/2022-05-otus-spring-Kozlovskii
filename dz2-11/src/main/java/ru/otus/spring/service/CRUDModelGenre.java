package ru.otus.spring.service;

import ru.otus.spring.models.Genre;

public interface CRUDModelGenre {
    Genre create(Genre Genre);

    void showById(long id);

    void showByName(String name);

    void showAll();

    boolean update(Genre obj);

    void deleteById(long id);
}