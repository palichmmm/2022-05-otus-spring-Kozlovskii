package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Author;
import ru.otus.spring.repositories.AuthorRepository;

import javax.persistence.NoResultException;

@Service
public class CRUDModelAuthorServiceImpl implements CRUDModelAuthor {
    private final AuthorRepository repository;
    private final IOService ioService;

    public CRUDModelAuthorServiceImpl(AuthorRepository repository, IOService ioService) {
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

    @Transactional
    @Override
    public void showById(long id) {
        repository.findById(id)
                .ifPresentOrElse(author -> ioService.outputString(String.valueOf(author)),
                        () -> ioService.outputString("Автора с ID=" + id + " не существует!"));
    }

    @Transactional
    @Override
    public void showByName(String name) {
        repository.findByName(name)
                .ifPresentOrElse(author -> ioService.outputString(String.valueOf(author)),
                        () -> ioService.outputString("Автора с NAME=" + name + " не существует!"));
    }

    @Transactional
    @Override
    public void showAll() {
        for (Author author : repository.findAll()) {
            ioService.outputString(String.valueOf(author));
        }
    }

    @Transactional
    @Override
    public boolean update(Author author) {
        if (repository.findById(author.getId()).isEmpty()) {
            throw new NoResultException("Автора с ID=" + author.getId() + " нет в базе!");
        } else {
            repository.updateAuthorById(author.getId(), author.getAuthorName());
            ioService.outputString("Автор с ID=" + author.getId() + " успешно обновлен!");
            return true;
        }
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        if (repository.deleteById(id)) {
            ioService.outputString("Автор с ID=" + id + " успешно удален!");
        }
    }
}
