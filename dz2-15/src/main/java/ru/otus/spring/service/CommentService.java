package ru.otus.spring.service;

import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(String id);

    List<Comment> findAllCommentByBook(Book book);

    Comment save(Comment comment);

    void deleteById(String id);
}
