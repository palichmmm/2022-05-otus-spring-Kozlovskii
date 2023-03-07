package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.models.Collection;

public interface CollectionRepository extends MongoRepository<Collection, String> {
}
