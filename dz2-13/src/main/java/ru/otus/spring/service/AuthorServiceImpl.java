package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Author;
import ru.otus.spring.repositories.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;
    private final IOService ioService;

    public AuthorServiceImpl(AuthorRepository repository, IOService ioService) {
        this.repository = repository;
        this.ioService = ioService;
    }

    @Transactional
    @Override
    public Author create(Author author) {
        Author resultAuthor = repository.save(author);
        ioService.outputString("Вставлена новая запись автора с ID=" + resultAuthor.getId());
        return resultAuthor;
    }

    @Transactional(readOnly = true)
    @Override
    public void showById(long id) {
        repository.findById(id)
                .ifPresentOrElse(author -> ioService.outputString(String.valueOf(author)),
                        () -> ioService.outputString("Автора с ID=" + id + " не существует!"));
    }

    @Transactional(readOnly = true)
    @Override
    public void showByName(String name) {
        repository.findByAuthorName(name)
                .ifPresentOrElse(author -> ioService.outputString(String.valueOf(author)),
                        () -> ioService.outputString("Автора с NAME=" + name + " не существует!"));
    }

    @Transactional(readOnly = true)
    @Override
    public void showAll() {
        for (Author author : repository.findAll()) {
            ioService.outputString(String.valueOf(author));
        }
    }

    @Transactional
    @Override
    public void update(long id, String name) {
        repository.findById(id).ifPresent(author -> {
            author.setAuthorName(name);
            repository.save(author);
        });
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);

    }
}
