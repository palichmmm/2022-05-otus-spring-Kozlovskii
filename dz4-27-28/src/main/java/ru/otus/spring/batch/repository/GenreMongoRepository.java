package ru.otus.spring.batch.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.batch.models.mongo.GenreDocument;

public interface GenreMongoRepository extends MongoRepository<GenreDocument, String> {

    GenreDocument findGenreDocumentByGenreName(String name);
}
