package ru.otus.spring.service;

import ru.otus.spring.models.Comment;

public interface CommentService {
    Comment save(Comment comment);

    Comment findById(long id);

    void deleteById(long id);

    long count();
}
