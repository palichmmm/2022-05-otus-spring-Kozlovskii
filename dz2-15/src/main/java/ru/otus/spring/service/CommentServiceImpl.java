package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;

    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public Comment findById(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("КОММЕНТАРИЙ С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
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
        Comment updateComment = repository.findById(comment.getId()).orElseThrow(() ->
                new RuntimeException("КОММЕНТАРИЙ С ID - " + comment.getId() + " НЕ СУЩЕСТВУЕТ!"));
        updateComment.setComment(comment.getComment());
        return repository.save(updateComment);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
