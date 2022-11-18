package ru.otus.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, Long> {

    List<Book> findBookByBookName(String name);

    List<Book> findAll();
}
