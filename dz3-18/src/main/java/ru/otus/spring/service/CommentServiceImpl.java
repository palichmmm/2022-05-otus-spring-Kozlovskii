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

    public CommentServiceImpl(CommentRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
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
        return resultComment;
    }

    @Transactional(readOnly = true)
    @Override
    public void showById(long id) {
        repository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional
    @Override
    public void update(long id, String name) {
        repository.findById(id).ifPresent(comment -> {
            comment.setComment(name);
            repository.save(comment);
        });
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
