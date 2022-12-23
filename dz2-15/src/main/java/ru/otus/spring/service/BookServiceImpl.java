package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.CommentRepository;
import ru.otus.spring.repositories.GenreRepository;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    public BookServiceImpl(BookRepository repository, AuthorRepository authorRepository, GenreRepository genreRepository, CommentRepository commentRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
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
            book.setAuthor(authorRepository.findById(book.getAuthor().getId())
                    .orElseThrow(() -> new RuntimeException("Ошибка сохранения книги! Нет такого автора!")));
            book.setGenre(genreRepository.findById(book.getGenre().getId())
                    .orElseThrow(() -> new RuntimeException("Ошибка сохранения книги! Нет такого жанра!")));
            return repository.save(book);
        }
        Book bookUpdate = repository.findById(book.getId()).orElseThrow(() ->
                new RuntimeException("КНИГИ С Id - " + book.getId() + " НЕ СУЩЕСТВУЕТ!"));
        bookUpdate.setBookName(book.getBookName());
        return repository.save(bookUpdate);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        List<Comment> comments = commentRepository.findAllByBook_Id(id);
        if (comments.isEmpty()) {
            repository.deleteById(id);
        } else {
            commentRepository.deleteAllByBook_Id(id);
            repository.deleteById(id);
        }
    }

    @Transactional
    @Override
    public boolean existById(String id) {
        return repository.existsById(id);
    }

    @Transactional
    @Override
    public boolean existByName(String name) {
        return repository.existsByBookName(name);
    }
}
