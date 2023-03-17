package ru.otus.spring.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileStorageService {
    void init();

    Path save(MultipartFile file, String randomFileName);

    void delete(String serverName);

    void deleteAll();
}
