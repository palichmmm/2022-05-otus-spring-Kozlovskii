package ru.otus.spring.batch.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.batch.models.mongo.BookDocument;

public interface BookMongoRepository extends MongoRepository<BookDocument, String> {
}
