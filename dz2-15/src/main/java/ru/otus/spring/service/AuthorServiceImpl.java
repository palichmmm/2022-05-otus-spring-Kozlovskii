package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Author;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;

    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
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
        if (repository.existsById(author.getId())) {
            return repository.save(author);
        }
        throw new RuntimeException("АВТОР С ID - " + author.getId() + " НЕ СУЩЕСТВУЕТ!");
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        if (bookRepository.existsByAuthor_Id(id)) {
            throw new RuntimeException("Ошибка удаления! На этого автора ссылаются книги!");
        }
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean existById(String id) {
        return repository.existsById(id);
    }

    @Transactional
    @Override
    public boolean existByName(String name) {
        return repository.existsByAuthorName(name);
    }
}
