package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.models.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, Long> {

    Mono<Book> findById(String id);

    Mono<Void> deleteById(String id);
}
