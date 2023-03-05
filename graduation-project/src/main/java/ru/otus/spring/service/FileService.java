package ru.otus.spring.service;

import ru.otus.spring.models.File;

import java.util.List;

public interface FileService {

    File saveToDb(File file);

    List<File> findAllByUserName();

    boolean existsByOriginalNameAndUserName(String originalName);

    void deleteByFileName(String fileName);
}
