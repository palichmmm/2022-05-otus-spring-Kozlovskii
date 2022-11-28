package ru.otus.spring.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.models.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    @Override
    @EntityGraph(value = "author-genre-graph")
    List<Book> findAll();
}
