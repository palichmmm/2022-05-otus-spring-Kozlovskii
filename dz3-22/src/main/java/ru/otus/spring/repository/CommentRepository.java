package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.models.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, Long> {

    Flux<Comment> findAllByBook_Id(String id);

    Mono<Void> deleteById(String id);

    Mono<Long> countAllByBook_Id(String id);
}
