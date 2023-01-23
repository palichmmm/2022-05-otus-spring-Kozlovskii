package ru.otus.spring.batch.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.batch.models.mongo.GenreDocument;

public interface GenreMongoRepository extends MongoRepository<GenreDocument, String> {

//    @Cacheable(cacheManager = "cacheManager", cacheNames = "genre")
    GenreDocument findGenreDocumentByGenreName(String name);
}
