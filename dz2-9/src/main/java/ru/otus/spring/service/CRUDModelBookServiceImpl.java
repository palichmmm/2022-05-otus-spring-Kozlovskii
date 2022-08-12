package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.models.Book;

import java.util.List;

@Service
public class CRUDModelBookServiceImpl implements CRUDModelService<Book>{
    private final BookDao dao;

    public CRUDModelBookServiceImpl(BookDao dao) {
        this.dao = dao;
    }

    @Override
    public long create(Book book) {
        try {
            dao.insert(book);
            return book.getId();
        } catch (Exception err) {
            return CREATE_ERROR;
        }
    }

    @Override
    public Book readById(long id) {
        return dao.getById(id);
    }

    @Override
    public List<Book> readAll() {
        return dao.getAll();
    }

    @Override
    public boolean update(Book book) {
        if (dao.getById(book.getId()) == null) {
            return false;
        } else {
            dao.update(book);
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
