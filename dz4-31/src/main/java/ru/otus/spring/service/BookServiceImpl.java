package ru.otus.spring.service;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.integration.service.LetterGateway;
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
    private final AclPermissionService aclPermissionService;
    private final LetterGateway letterGateway;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           GenreRepository genreRepository,
                           AclPermissionService aclPermissionService, LetterGateway letterGateway) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.aclPermissionService = aclPermissionService;
        this.letterGateway = letterGateway;
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
        if (book.getId() == 0) {
            Book newBook = bookRepository.save(book);
            aclPermissionService.savePermission(Book.class, newBook.getId(), BasePermission.WRITE);
            return newBook;
        }
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
        return letterGateway.bookReplacementLetters(bookRepository.findAll());
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
