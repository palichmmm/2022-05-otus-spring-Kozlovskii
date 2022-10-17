package ru.otus.spring.repositories;

import ru.otus.spring.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Optional<Book> findById(long id);

    List<Book> findByName(String name);

    List<Book> findAll();

    Book save(Book book);

    void updateBookById(long id, String name);

    boolean deleteById(long id);
}
