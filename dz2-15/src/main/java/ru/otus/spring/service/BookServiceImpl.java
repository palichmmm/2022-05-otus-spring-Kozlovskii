package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Book;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.GenreRepository;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookServiceImpl(BookRepository repository,
                           AuthorRepository authorRepository,
                           GenreRepository genreRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Transactional
    @Override
    public Book findById(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("КНИГИ С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
    }

    @Transactional
    @Override
    public List<Book> findByName(String name) {
        return repository.findByBookName(name);
    }

    @Transactional
    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            return repository.save(book);
        }
        Book bookUpdate = repository.findById(book.getId()).orElseThrow(() ->
                new RuntimeException("КНИГИ С ID - " + book.getId() + " НЕ СУЩЕСТВУЕТ!"));
        bookUpdate.setBookName(book.getBookName());
        return repository.save(bookUpdate);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
