package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.models.Genre;

import java.util.List;

@Service
public class CRUDModelGenreServiceImpl implements CRUDModelService<Genre> {
    private final GenreDao dao;

    public CRUDModelGenreServiceImpl(GenreDao dao) {
        this.dao = dao;
    }

    @Override
    public int create(Genre genre) {
        try {
            dao.insert(genre);
            return genre.getId();
        } catch (Exception err) {
            return CREATE_ERROR;
        }
    }

    @Override
    public Genre read(int id) {
        return dao.getById(id);
    }

    @Override
    public List<Genre> readAll() {
        return dao.getAll();
    }

    @Override
    public boolean update(int id) {
        try {
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            dao.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}
