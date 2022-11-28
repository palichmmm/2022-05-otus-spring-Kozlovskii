package ru.otus.spring.service;

import ru.otus.spring.models.Genre;

import java.util.List;

public interface GenreService {
    Genre save(Genre Genre);

    Genre findById(long id);

    List<Genre> findAll();

    void deleteById(long id);
}