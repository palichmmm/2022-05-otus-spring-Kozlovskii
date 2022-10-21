package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.GenreRepository;

import javax.persistence.NoResultException;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repository;
    private final IOService ioService;

    public GenreServiceImpl(GenreRepository repository, IOService ioService) {
        this.repository = repository;
        this.ioService = ioService;
    }

    @Transactional
    @Override
    public Genre create(Genre genre) {
        if (repository.findByGenreName(genre.getGenreName()).isEmpty()) {
            Genre resultGenre = repository.save(genre);
            ioService.outputString("Вставлена новая запись жанра с ID=" + genre.getId());
            return resultGenre;
        } else {
            throw new RuntimeException("Жанр с NAME=" + genre.getGenreName() + " уже есть в базе!");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public void showById(long id) {
        repository.findById(id)
                .ifPresentOrElse(genre -> ioService.outputString(String.valueOf(genre)),
                        () -> ioService.outputString("Жанр с ID=" + id + " не существует!"));
    }

    @Transactional(readOnly = true)
    @Override
    public void showByName(String name) {
        repository.findByGenreName(name)
                .ifPresentOrElse(genre -> ioService.outputString(String.valueOf(genre)),
                        () -> ioService.outputString("Жанра с NAME=" + name + " не существует!"));
    }

    @Transactional(readOnly = true)
    @Override
    public void showAll() {
        for (Genre genre : repository.findAll()) {
            ioService.outputString(String.valueOf(genre));
        }
    }

    @Transactional
    @Override
    public boolean update(long id, String name) {
        Optional<Genre> genre = repository.findById(id);
        if (genre.isEmpty()) {
            throw new NoResultException("Жанр с ID=" + id + " нет в базе!");
        } else {
            genre.get().setGenreName(name);
            repository.save(genre.get());
            ioService.outputString("Жанр с ID=" + id + " успешно обновлен!");
            return true;
        }
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
