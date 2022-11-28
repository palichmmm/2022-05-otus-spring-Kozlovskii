package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.GenreRepository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Transactional
    @Override
    public Book save(Book book) {
        Author author = authorRepository.findById(book.getAuthor().getId()).orElseThrow(RuntimeException::new);
        if (author == null) {
            throw new RuntimeException("Автора с ID=" + book.getAuthor().getId() + " не существует!");
        }
        Genre genre = genreRepository.findById(book.getGenre().getId()).orElseThrow(RuntimeException::new);
        if (genre == null) {
            throw new RuntimeException("Жанра с ID=" + book.getGenre().getId() + " не существует!");
        }
        book.setAuthor(author);
        book.setGenre(genre);
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    @Override
    public Book findById(long id) {
        return bookRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public void findAllCommentsBookById(long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new NoResultException("Книги с ID=" + id + " нет в базе!");
        }
        for (Comment comment : book.get().getComments()) {
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public long count() {
        return bookRepository.count();
    }
}
