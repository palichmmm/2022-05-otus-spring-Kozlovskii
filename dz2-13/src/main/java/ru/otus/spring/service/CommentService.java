package ru.otus.spring.service;

import ru.otus.spring.models.Comment;

public interface CommentService {
    Comment create(Comment comment);

    void showById(long id);

    void showAll();

    boolean update(long id, String name);

    void deleteById(long id);
}
