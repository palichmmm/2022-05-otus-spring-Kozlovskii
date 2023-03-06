package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.models.File;

import java.util.List;

public interface FileRepository extends MongoRepository<File, String> {

    boolean existsByOriginalNameAndUserName(String originalName, String userName);

    File findByFileNameAndUserName(String fileName, String userName);

    File findBySerialNumberAndUserName(String serialNumber, String userName);

    List<File> findAllByUserName(String userName);

    void deleteByFileName(String fileName);
}
