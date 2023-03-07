package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.models.Tag;

public interface TagRepository extends MongoRepository<Tag, String> {
    void deleteByFileName(String fileName);
}
