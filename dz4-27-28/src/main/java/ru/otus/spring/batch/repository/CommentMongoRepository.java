package ru.otus.spring.batch.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.batch.models.mongo.CommentDocument;

public interface CommentMongoRepository extends MongoRepository<CommentDocument, String> {
}
