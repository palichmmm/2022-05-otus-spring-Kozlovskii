package ru.otus.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, Long> {

    Optional<Comment> findById(String id);

    List<Comment> findAllByBook_Id(String id);

    void deleteById(String id);

    void deleteAllByBook_Id(String id);

    boolean existsByBook_Id(String id);
}
