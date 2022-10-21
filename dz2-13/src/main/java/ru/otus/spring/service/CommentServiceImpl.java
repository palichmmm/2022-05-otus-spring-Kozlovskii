package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.CommentRepository;

import javax.persistence.NoResultException;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
    private final BookRepository bookRepository;
    private final IOService ioService;

    public CommentServiceImpl(CommentRepository repository, BookRepository bookRepository, IOService ioService) {
        this.repository = repository;
        this.bookRepository = bookRepository;
        this.ioService = ioService;
    }

    @Transactional
    @Override
    public Comment create(Comment comment) {
        Optional<Book> book = bookRepository.findById(comment.getBook().getId());
        if (book.isEmpty()) {
            throw new NoResultException("Книги с ID=" + comment.getBook().getId() + " нет в базе!");
        }
        comment.setBook(book.get());
        Comment resultComment = repository.save(comment);
        ioService.outputString("Вставлен новый комментарий на книгу с ID=" + resultComment.getBook().getId());
        return resultComment;
    }

    @Transactional(readOnly = true)
    @Override
    public void showById(long id) {
        repository.findById(id)
                .ifPresentOrElse(comment -> ioService.outputString(String.valueOf(comment)),
                        () -> ioService.outputString("Комментария с ID=" + id + " не существует!"));
    }

    @Transactional(readOnly = true)
    @Override
    public void showAll() {
        for (Comment comment : repository.findAll()) {
            ioService.outputString(String.valueOf(comment));
        }
    }

    @Transactional
    @Override
    public boolean update(long id, String name) {
        Optional<Comment> comment = repository.findById(id);
        if (comment.isEmpty()) {
            throw new NoResultException("Комментария с ID=" + id + " нет в базе!");
        } else {
            comment.get().setComment(name);
            repository.save(comment.get());
            ioService.outputString("Комментарий с ID=" + id + " успешно обновлен!");
            return true;
        }
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
