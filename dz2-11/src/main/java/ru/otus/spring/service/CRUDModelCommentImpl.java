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
public class CRUDModelCommentImpl implements CRUDModelComment {
    private final CommentRepository repository;
    private final BookRepository bookRepository;
    private final IOService ioService;

    public CRUDModelCommentImpl(CommentRepository repository, BookRepository bookRepository, IOService ioService) {
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

    @Transactional
    @Override
    public void showById(long id) {
        repository.findById(id)
                .ifPresentOrElse(comment -> ioService.outputString(String.valueOf(comment)),
                        () -> ioService.outputString("Комментария с ID=" + id + " не существует!"));
    }

    @Transactional
    @Override
    public void showAll() {
        for (Comment comment : repository.findAll()) {
            ioService.outputString(String.valueOf(comment));
        }
    }

    @Transactional
    @Override
    public boolean update(Comment comment) {
        if (repository.findById(comment.getId()).isEmpty()) {
            throw new NoResultException("Комментария с ID=" + comment.getId() + " нет в базе!");
        } else {
            repository.updateById(comment.getId(), comment.getComment());
            ioService.outputString("Комментарий с ID=" + comment.getId() + " успешно обновлен!");
            return true;
        }
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
