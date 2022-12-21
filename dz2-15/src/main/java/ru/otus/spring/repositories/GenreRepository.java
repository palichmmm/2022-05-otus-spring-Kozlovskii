package ru.otus.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, Long> {

    Optional<Genre> findById(String id);

    List<Genre> findByGenreName(String name);

    void deleteById(String id);
}
