package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;

    private final BookRepository bookRepository;

    private final IOService ioService;

    public AuthorServiceImpl(AuthorRepository repository, BookRepository bookRepository, IOService ioService) {
        this.repository = repository;
        this.bookRepository = bookRepository;
        this.ioService = ioService;
    }

    @Transactional(readOnly = true)
    @Override
    public Author findById(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("АВТОР С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> findByName(String name) {
        return repository.findByAuthorName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            return repository.save(author);
        }
        Author authorUpdate = repository.findById(author.getId()).orElseThrow(() ->
                new RuntimeException("АВТОР С ID - " + author.getId() + " НЕ СУЩЕСТВУЕТ!"));
        authorUpdate.setAuthorName(author.getAuthorName());
        return repository.save(authorUpdate);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        List<Book> books = bookRepository.findAllByAuthor_Id(id);
        if (books.isEmpty()) {
            repository.deleteById(id);
        } else {
            books.forEach(book -> ioService.outputString(String.valueOf(book)));
            throw new RuntimeException("Ошибка удаления! На этого автора ссылаются книги!");

        }
    }
}
