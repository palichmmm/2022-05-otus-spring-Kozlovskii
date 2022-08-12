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
    public long create(Genre genre) {
        try {
            dao.insert(genre);
            return genre.getId();
        } catch (Exception err) {
            return CREATE_ERROR;
        }
    }

    @Override
    public Genre readById(long id) {
        return dao.getById(id);
    }

    @Override
    public List<Genre> readAll() {
        return dao.getAll();
    }

    @Override
    public boolean update(Genre genre) {
        if (dao.getById(genre.getId()) == null) {
            return false;
        } else {
            dao.update(genre);
            return true;
        }
    }

    @Override
    public boolean deleteById(long id) {
        try {
            dao.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}
