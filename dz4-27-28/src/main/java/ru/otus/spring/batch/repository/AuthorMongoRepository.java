package ru.otus.spring.batch.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.batch.models.mongo.AuthorDocument;

public interface AuthorMongoRepository extends MongoRepository<AuthorDocument, String> {

//    @Cacheable(cacheManager = "cacheManager", cacheNames = "author")
    AuthorDocument findAuthorDocumentByAuthorName(String name);
}
