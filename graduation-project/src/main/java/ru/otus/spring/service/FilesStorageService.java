package ru.otus.spring.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.models.File;

import java.util.List;

public interface FilesStorageService {
    void init();

    void save(MultipartFile file);

    Resource load(String filename);

    public boolean delete(String filename);

    void deleteAll();

    List<File> loadAll();
}
