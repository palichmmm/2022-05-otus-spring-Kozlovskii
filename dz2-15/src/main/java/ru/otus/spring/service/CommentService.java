package ru.otus.spring.service;

import ru.otus.spring.models.Comment;

import java.util.List;

public interface CommentService {
    Comment findById(String id);

    List<Comment> findAllCommentByBookId(String id);

    Comment save(Comment comment);

    void deleteById(String id);
}
