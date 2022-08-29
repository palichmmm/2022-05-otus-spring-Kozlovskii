package ru.otus.spring.dao;

import ru.otus.spring.models.Author;
import ru.otus.spring.models.Genre;

import java.util.List;

public interface GenreDao {
    long count();
    void insert(Genre genre);
    void update(Genre genre);
    Genre getById(long id);
    List<Genre> getAll();
    void deleteById(long id);
}
