package ru.otus.spring.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Author;
import ru.otus.spring.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @CircuitBreaker(name = "AuthorFindOne", fallbackMethod = "defaultAuthor")
    @Transactional
    @Override
    public Author save(Author author) {
        return repository.save(author);
    }

    @CircuitBreaker(name = "AuthorFindOne", fallbackMethod = "defaultAuthor")
    @Transactional(readOnly = true)
    @Override
    public Author findById(long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("АВТОР С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
    }

    @CircuitBreaker(name = "AuthorFindOne", fallbackMethod = "defaultAuthor")
    @Transactional
    @Override
    public Author findByName(String name) {
        return repository.findByAuthorName(name).orElseThrow(() -> new RuntimeException("АВТОР С ИМЕНЕМ - " + name + " НЕ СУЩЕСТВУЕТ!"));
    }

    @CircuitBreaker(name = "AuthorFindAll", fallbackMethod = "defaultAuthorList")
    @Transactional(readOnly = true)
    @Override
    public List<Author> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public long count() {
        return repository.count();
    }

    public List<Author> defaultAuthorList(Exception e) {
        List<Author> list = new ArrayList<>();
        list.add(new Author(1, "Test Author1"));
        list.add(new Author(2, "Test Author2"));
        return list;
    }

    public Author defaultAuthor(Exception e) {
        return new Author(1, "Test Author1");
    }
}
