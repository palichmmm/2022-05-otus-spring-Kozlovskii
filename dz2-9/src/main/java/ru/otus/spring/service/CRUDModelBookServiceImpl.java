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
    public int create(Book book) {
        try {
            dao.insert(book);
            return book.getId();
        } catch (Exception err) {
            return CREATE_ERROR;
        }
    }

    @Override
    public Book readById(int id) {
        return dao.getById(id);
    }

    @Override
    public List<Book> readAll() {
        return dao.getAll();
    }

    @Override
    public boolean updateById(int id) {
        try {
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    @Override
    public boolean deleteById(int id) {
        try {
            dao.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}
