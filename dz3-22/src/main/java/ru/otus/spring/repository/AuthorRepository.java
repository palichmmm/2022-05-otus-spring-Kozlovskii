package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.models.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, Long> {

    Mono<Author> findById(String id);
}
