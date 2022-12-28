package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.models.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, Long> {

//    Optional<Genre> findById(String id);
//
//    List<Genre> findByGenreName(String name);
//
//    void deleteById(String id);
//
//    boolean existsById(String id);
//
//    boolean existsByGenreName(String name);
}
