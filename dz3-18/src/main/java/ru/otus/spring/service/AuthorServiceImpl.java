package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Author;
import ru.otus.spring.repositories.AuthorRepository;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public Author save(Author author) {
        return repository.save(author);
    }

    @Transactional(readOnly = true)
    @Override
    public Author findById(long id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional
    @Override
    public Author findByName(String name) {
        return repository.findByAuthorName(name).orElseThrow(RuntimeException::new);
    }

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
}
