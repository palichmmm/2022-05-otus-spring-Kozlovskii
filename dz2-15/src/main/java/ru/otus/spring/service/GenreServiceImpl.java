package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.GenreRepository;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repository;

    private final BookRepository bookRepository;

    public GenreServiceImpl(GenreRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
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
        if (repository.existsById(genre.getId())) {
            return repository.save(genre);
        }
        throw new RuntimeException("ЖАНР С ID - " + genre.getId() + " НЕ СУЩЕСТВУЕТ!");
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        if (bookRepository.existsByGenre_Id(id)) {
            throw new RuntimeException("Ошибка удаления! На этот жанр ссылаются книги!");
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
        return repository.existsByGenreName(name);
    }
}
