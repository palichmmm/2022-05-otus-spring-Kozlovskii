package ru.otus.spring.service;

import ru.otus.spring.models.Comment;

public interface CRUDModelComment {
    Comment create(Comment comment);

    void showById(long id);

    void showAll();

    boolean update(Comment comment);

    void deleteById(long id);
}
