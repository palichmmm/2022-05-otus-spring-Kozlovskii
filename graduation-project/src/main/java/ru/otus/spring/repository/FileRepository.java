package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.models.File;

public interface FileRepository extends MongoRepository<File, String> {

    boolean existsByOriginalName(String originalName);

    File findByFileName(String fileName);

    File findBySerialNumberAndUserName(String serialNumber, String userName);
}
