package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Author;
import ru.otus.spring.repositories.AuthorRepository;

import javax.persistence.NoResultException;
import java.util.Optional;

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
        if (repository.findByName(author.getAuthorName()).isEmpty()) {
            Author resultAuthor = repository.save(author);
            ioService.outputString("Вставлена новая запись автора с ID=" + resultAuthor.getId());
            return resultAuthor;
        } else {
            throw new RuntimeException("Автора с NAME=" + author.getAuthorName() + " уже есть в базе!");
        }
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
        repository.findByName(name)
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
    public boolean update(long id, String name) {
        Optional<Author> author = repository.findById(id);
        if (author.isEmpty()) {
            throw new NoResultException("Автора с ID=" + id + " нет в базе!");
        } else {
            author.get().setAuthorName(name);
            repository.save(author.get());
            ioService.outputString("Автор с ID=" + id + " успешно обновлен!");
            return true;
        }
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);

    }
}
