package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.models.TagFile;

public interface TagRepository extends MongoRepository<TagFile, String> {
    void deleteByFileName(String fileName);
}
