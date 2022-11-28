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
    public Book create(Book book) {
        Optional<Author> author = authorRepository.findById(book.getAuthor().getId());
        if (author.isEmpty()) {
            throw new NoResultException("Автора с ID=" + book.getAuthor().getId() + " не существует!");
        }
        Optional<Genre> genre = genreRepository.findById(book.getGenre().getId());
        if (genre.isEmpty()) {
            throw new NoResultException("Жанра с ID=" + book.getGenre().getId() + " не существует!");
        }
        book.setAuthor(author.get());
        book.setGenre(genre.get());
        if (!repository.findBookByBookName(book.getBookName()).contains(book)) {

            Book resultBook = repository.save(book);
            return resultBook;
        } else {
            throw new RuntimeException("Книга с NAME=" + book.getBookName() +
                    " AUTHOR=" + author.get() + " GENRE=" + genre.get() + " уже есть в базе!");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public void showById(long id) {
        repository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public void showByName(String name) {
        List<Book> books = repository.findBookByBookName(name);
        if (books.isEmpty()) {
        } else {
            for (Book book : books) {
            }
        }
    }

    @Transactional(readOnly = true)
    @Override
    public void showAllCommentsBookById(long id) {
        Optional<Book> book = repository.findById(id);
        if (book.isEmpty()) {
            throw new NoResultException("Книги с ID=" + id + " нет в базе!");
        }
        for (Comment comment : book.get().getComments()) {
        }
    }

    @Transactional(readOnly = true)
    @Override
    public void showAll() {
        for (Book book : repository.findAll()) {
        }
    }

    @Transactional
    @Override
    public void update(long id, String name) {
        repository.findById(id).ifPresent(book -> {
            book.setBookName(name);
            repository.save(book);
        });
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
