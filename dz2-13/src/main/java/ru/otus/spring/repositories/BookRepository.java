package ru.otus.spring.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.models.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph(value = "author-genre-graph")
    List<Book> findBookByBookName(String name);
    @Override
    @EntityGraph(value = "author-genre-graph")
    List<Book> findAll();
}
