package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.models.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, Long> {

//    Optional<Comment> findById(String id);
//
//    List<Comment> findAllByBook_Id(String id);
//
//    void deleteById(String id);
//
//    void deleteAllByBook_Id(String id);
//
//    boolean existsByBook_Id(String id);
}
