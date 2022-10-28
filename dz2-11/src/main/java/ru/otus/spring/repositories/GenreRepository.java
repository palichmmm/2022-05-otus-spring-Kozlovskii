package ru.otus.spring.repositories;

import ru.otus.spring.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    Optional<Genre> findById(long id);

    Optional<Genre> findByName(String name);

    List<Genre> findAll();

    Genre save(Genre genre);

    void deleteById(long id);
}
