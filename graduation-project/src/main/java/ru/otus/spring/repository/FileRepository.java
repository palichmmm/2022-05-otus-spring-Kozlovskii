package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.models.Mp3FileDescriptor;

public interface FileRepository extends MongoRepository<Mp3FileDescriptor, String> {

    boolean existsByOriginalName(String originalName);

    Mp3FileDescriptor findByFileName(String fileName);

    Mp3FileDescriptor findBySerialNumberAndUserName(String serialNumber, String userName);
}
