package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.models.Author;

import java.util.List;

@Service
public class CRUDModelAuthorServiceImpl implements CRUDModelAuthor {
    private final AuthorDao authorDao;

    public CRUDModelAuthorServiceImpl(AuthorDao dao) {
        this.authorDao = dao;
    }

    @Override
    public long create(Author author) {
        try {
            authorDao.insert(author);
            return author.getId();
        } catch (Exception err) {
            throw new RuntimeException("Ошибка записи Автора в базу!!!");
        }
    }

    @Override
    public Author readById(long id) {
        return authorDao.getById(id);
    }

    @Override
    public List<Author> readAll() {
        return authorDao.getAll();
    }

    @Override
    public boolean update(Author author) {
        if (authorDao.getById(author.getId()) == null) {
            return false;
        } else {
            authorDao.update(author);
            return true;
        }
    }

    @Override
    public boolean deleteById(long id) {
        try {
            authorDao.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}
