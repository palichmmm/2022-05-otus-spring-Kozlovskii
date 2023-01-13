package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;

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
            throw new RuntimeException("АВТОР С ID=" + book.getAuthor().getId() + " НЕ СУЩЕСТВУЕТ!");
        }
        Genre genre = genreRepository.findById(book.getGenre().getId()).orElseThrow(RuntimeException::new);
        if (genre == null) {
            throw new RuntimeException("ЖАНР С ID=" + book.getGenre().getId() + " НЕ СУЩЕСТВУЕТ!");
        }
        book.setAuthor(author);
        book.setGenre(genre);
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    @Override
    public Book findById(long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("КНИГИ С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
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
