package ru.otus.spring.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repository;

    public GenreServiceImpl(GenreRepository repository) {
        this.repository = repository;
    }

    @CircuitBreaker(name = "GenreFindOne", fallbackMethod = "defaultGenre")
    @Transactional
    @Override
    public Genre save(Genre genre) {
        return repository.save(genre);
    }

    @CircuitBreaker(name = "GenreFindOne", fallbackMethod = "defaultGenre")
    @Transactional(readOnly = true)
    @Override
    public Genre findById(long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("ЖАНР С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
    }

    @CircuitBreaker(name = "GenreFindOne", fallbackMethod = "defaultGenre")
    @Transactional
    @Override
    public Genre findByName(String name) {
        return repository.findByGenreName(name).orElseThrow(() -> new RuntimeException("ЖАНР С НАЗВАНИЕМ - " + name + " НЕ СУЩЕСТВУЕТ!"));
    }

    @CircuitBreaker(name = "GenreFindAll", fallbackMethod = "defaultGenreList")
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

    public List<Genre> defaultGenreList(Exception e) {
        List<Genre> list = new ArrayList<>();
        list.add(new Genre(1, "Test Genre1"));
        list.add(new Genre(2, "Test Genre2"));
        return list;
    }

    public Genre defaultGenre(Exception e) {
        return new Genre(1, "Test Genre1");
    }
}
