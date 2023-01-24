package ru.otus.spring.batch.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.batch.models.mongo.AuthorDocument;

public interface AuthorMongoRepository extends MongoRepository<AuthorDocument, String> {

    AuthorDocument findAuthorDocumentByAuthorName(String name);
}
