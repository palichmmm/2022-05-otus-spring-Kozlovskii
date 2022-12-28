package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.models.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, Long> {

    Mono<Genre> findById(String id);
}
