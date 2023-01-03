package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.models.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Mono<Boolean> existsByAuthor_Id(String id);

    Mono<Boolean> existsByGenre_Id(String id);
}
