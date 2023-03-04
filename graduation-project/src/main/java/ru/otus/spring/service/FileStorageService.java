package ru.otus.spring.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileStorageService {
    void init();

    Path save(MultipartFile file, String randomFileName);

    Resource load(String filename);

    Resource loadZip(String filename);

    void delete(String serverName);

    void deleteAll();
}
