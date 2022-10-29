package ru.otus.spring.repositories;

import ru.otus.spring.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> findById(long id);

    Comment save(Comment comment);

    List<Comment> findAllByBookId(long id);

    void deleteById(long id);
}
