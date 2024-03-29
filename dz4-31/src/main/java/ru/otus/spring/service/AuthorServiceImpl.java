package ru.otus.spring.service;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.integration.service.LetterGateway;
import ru.otus.spring.models.Author;
import ru.otus.spring.repository.AuthorRepository;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;
    private final AclPermissionService aclPermissionService;
    private final LetterGateway letterGateway;

    public AuthorServiceImpl(AuthorRepository repository, AclPermissionService aclPermissionService, LetterGateway letterGateway) {
        this.repository = repository;
        this.aclPermissionService = aclPermissionService;
        this.letterGateway = letterGateway;
    }

    @Transactional
    @Override
    public Author save(Author author) {
        if (author.getId() == 0) {
            Author newAuthor = repository.save(author);
            aclPermissionService.savePermission(Author.class, newAuthor.getId(), BasePermission.WRITE);
            return newAuthor;
        }
        return repository.save(author);
    }

    @Transactional(readOnly = true)
    @Override
    public Author findById(long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("АВТОР С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
    }

    @Transactional
    @Override
    public Author findByName(String name) {
        return repository.findByAuthorName(name).orElseThrow(() -> new RuntimeException("АВТОР С ИМЕНЕМ - " + name + " НЕ СУЩЕСТВУЕТ!"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> findAll() {
        return letterGateway.authorReplacementLetters(repository.findAll());
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
