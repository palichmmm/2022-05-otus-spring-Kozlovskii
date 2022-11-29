package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.GenreRepository;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repository;

    public GenreServiceImpl(GenreRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public Genre save(Genre genre) {
        return repository.save(genre);
    }

    @Transactional(readOnly = true)
    @Override
    public Genre findById(long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("ЖАНР С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
    }

    @Transactional
    @Override
    public Genre findByName(String name) {
        return repository.findByGenreName(name).orElseThrow(() -> new RuntimeException("ЖАНР С НАЗВАНИЕМ - " + name + " НЕ СУЩЕСТВУЕТ!"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }
}
