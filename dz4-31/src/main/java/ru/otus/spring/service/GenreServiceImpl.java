package ru.otus.spring.service;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.integration.service.LetterGateway;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repository;
    private final AclPermissionService aclPermissionService;
    private final LetterGateway letterGateway;

    public GenreServiceImpl(GenreRepository repository, AclPermissionService aclPermissionService, LetterGateway letterGateway) {
        this.repository = repository;
        this.aclPermissionService = aclPermissionService;
        this.letterGateway = letterGateway;
    }

    @Transactional
    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == 0) {
            Genre newGenre = repository.save(genre);
            aclPermissionService.savePermission(Genre.class, newGenre.getId(), BasePermission.WRITE);
            return newGenre;
        }
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
        return letterGateway.genreReplacementLetters(repository.findAll());
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
