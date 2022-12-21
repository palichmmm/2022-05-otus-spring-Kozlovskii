package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.CommentRepository;

import java.util.List;
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
    public Optional<Comment> findById(String id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public List<Comment> findAllCommentByBook(Book book) {
        return repository.findAllByBook(book);
    }

    @Transactional
    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            return repository.save(comment);
        }
        repository.save(comment);
        return comment;
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
