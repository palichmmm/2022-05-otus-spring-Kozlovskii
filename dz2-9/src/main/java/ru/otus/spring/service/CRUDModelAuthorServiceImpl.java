package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.models.Author;

import java.util.List;

@Service
public class CRUDModelAuthorServiceImpl implements CRUDModelService<Author> {
    private final AuthorDao authorDao;

    public CRUDModelAuthorServiceImpl(AuthorDao dao) {
        this.authorDao = dao;
    }

    @Override
    public int create(Author author) {
        try {
            authorDao.insert(author);
            return author.getId();
        } catch (Exception err) {
            return CREATE_ERROR;
        }
    }

    @Override
    public Author readById(int id) {
        return authorDao.getById(id);
    }

    @Override
    public List<Author> readAll() {
        return authorDao.getAll();
    }

    @Override
    public boolean updateById(int id) {
        try {
            Author author = authorDao.getById(id);
            System.out.println(author);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    @Override
    public boolean deleteById(int id) {
        try {
            authorDao.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}
