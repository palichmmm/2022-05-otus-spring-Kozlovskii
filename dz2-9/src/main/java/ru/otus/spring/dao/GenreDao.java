package ru.otus.spring.dao;

import ru.otus.spring.models.Genre;

import java.util.List;

public interface GenreDao {
    int count();
    void insert(Genre genre);
    Genre getById(int id);
    List<Genre> getAll();
    void deleteById(int id);
}
