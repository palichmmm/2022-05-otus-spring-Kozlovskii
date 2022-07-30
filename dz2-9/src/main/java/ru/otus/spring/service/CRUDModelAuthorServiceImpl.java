package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.models.Author;

import java.util.List;

@Service
public class CRUDModelAuthorServiceImpl implements CRUDModelService<Author> {
    private final AuthorDao dao;

    public CRUDModelAuthorServiceImpl(AuthorDao dao) {
        this.dao = dao;
    }

    @Override
    public int create(Author author) {
        try {
            dao.insert(author);
            return author.getId();
        } catch (Exception err) {
            return CREATE_ERROR;
        }
    }

    @Override
    public Author read(int id) {
        return dao.getById(id);
    }

    @Override
    public List<Author> readAll() {
        return dao.getAll();
    }

    @Override
    public boolean update(int id) {
        try {
            Author author = dao.getById(id);
            System.out.println(author);
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
