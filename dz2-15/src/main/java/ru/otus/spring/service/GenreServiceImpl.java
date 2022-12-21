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

    @Transactional(readOnly = true)
    @Override
    public Genre findById(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("ЖАНРА С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> findByName(String name) {
        return repository.findByGenreName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == null) {
            return repository.save(genre);
        }
        Genre genreUpdate = repository.findById(genre.getId()).orElseThrow(() ->
                new RuntimeException("ЖАНР С ID - " + genre.getId() + " НЕ СУЩЕСТВУЕТ!"));
        genreUpdate.setGenreName(genre.getGenreName());
        return repository.save(genreUpdate);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
