package ru.otus.spring.service;

import ru.otus.spring.models.Genre;

import java.util.List;

public interface GenreService {

    Genre findById(String id);

    List<Genre> findByName(String name);

    List<Genre> findAll();

    Genre save(Genre genre);

    void deleteById(String id);

    boolean existById(String id);

    boolean existByName(String name);
}
