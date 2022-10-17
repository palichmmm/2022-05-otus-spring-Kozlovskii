package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.GenreRepository;

import javax.persistence.NoResultException;

@Service
public class CRUDModelGenreServiceImpl implements CRUDModelGenre {
    private final GenreRepository repository;
    private final IOService ioService;

    public CRUDModelGenreServiceImpl(GenreRepository repository, IOService ioService) {
        this.repository = repository;
        this.ioService = ioService;
    }

    @Transactional
    @Override
    public Genre create(Genre genre) {
        if (repository.findByName(genre.getGenreName()).isEmpty()) {
            Genre resultGenre = repository.save(genre);
            ioService.outputString("Вставлена новая запись жанра с ID=" + genre.getId());
            return resultGenre;
        } else {
            throw new RuntimeException("Жанр с NAME=" + genre.getGenreName() + " уже есть в базе!");
        }
    }

    @Transactional
    @Override
    public void showById(long id) {
        repository.findById(id)
                .ifPresentOrElse(genre -> ioService.outputString(String.valueOf(genre)),
                        () -> ioService.outputString("Жанр с ID=" + id + " не существует!"));
    }

    @Transactional
    @Override
    public void showByName(String name) {
        repository.findByName(name)
                .ifPresentOrElse(genre -> ioService.outputString(String.valueOf(genre)),
                        () -> ioService.outputString("Жанра с NAME=" + name + " не существует!"));
    }

    @Transactional
    @Override
    public void showAll() {
        for (Genre genre : repository.findAll()) {
            ioService.outputString(String.valueOf(genre));
        }
    }

    @Transactional
    @Override
    public boolean update(Genre genre) {
        if (repository.findById(genre.getId()).isEmpty()) {
            throw new NoResultException("Жанр с ID=" + genre.getId() + " нет в базе!");
        } else {
            repository.updateGenreById(genre.getId(), genre.getGenreName());
            ioService.outputString("Жанр с ID=" + genre.getId() + " успешно обновлен!");
            return true;
        }
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
