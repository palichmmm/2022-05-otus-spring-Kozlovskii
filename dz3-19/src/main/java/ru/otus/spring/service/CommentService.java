package ru.otus.spring.service;

import ru.otus.spring.models.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findAllCommentByBookId(long bookId);
    Comment save(Comment comment);

    Comment findById(long id);

    void deleteById(long id);

    long count();

    long countAllByBookId(long id);
}
