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
public class CRUDModelBookServiceImpl implements CRUDModelBook {
    private final BookRepository repository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final IOService ioService;

    public CRUDModelBookServiceImpl(BookRepository repository,
                                    AuthorRepository authorRepository,
                                    GenreRepository genreRepository,
                                    IOService ioService) {
        this.repository = repository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.ioService = ioService;
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
        if (!repository.findByName(book.getBookName()).contains(book)) {
            Book resultBook = repository.save(book);
            ioService.outputString("Вставлена новая книга с ID=" + resultBook.getId());
            return resultBook;
        } else {
            throw new RuntimeException("Книга с NAME=" + book.getBookName() +
                    " AUTHOR=" + author.get() + " GENRE=" + genre.get() + " уже есть в базе!");
        }
    }

    @Transactional
    @Override
    public void showById(long id) {
        repository.findById(id)
                .ifPresentOrElse(book -> ioService.outputString(String.valueOf(book)),
                        () -> ioService.outputString("Книги с ID=" + id + " не существует!"));
    }

    @Transactional
    @Override
    public void showByName(String name) {
        List<Book> books = repository.findByName(name);
        if (books.isEmpty()) {
            ioService.outputString("Книг с NAME=" + name + " не существует!");
        } else {
            for (Book book : books) {
                ioService.outputString(String.valueOf(book));
            }
        }
    }

    @Transactional
    @Override
    public void showAllCommentsBookById(long id) {
        Optional<Book> book = repository.findById(id);
        if (book.isEmpty()) {
            throw new NoResultException("Книги с ID=" + id + " нет в базе!");
        }
        for (Comment comment : book.get().getComments()) {
            ioService.outputString(String.valueOf(comment));
        }
    }

    @Transactional
    @Override
    public void showAll() {
        for (Book book : repository.findAll()) {
            ioService.outputString(String.valueOf(book));
        }
    }

    @Transactional
    @Override
    public boolean update(Book book) {
        if (repository.findById(book.getId()).isEmpty()) {
            throw new NoResultException("Книги с ID=" + book.getId() + " нет в базе!");
        } else {
            repository.updateBookById(book.getId(), book.getBookName());
            ioService.outputString("Книга с ID=" + book.getId() + " успешно обновлена!");
            return true;
        }
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
