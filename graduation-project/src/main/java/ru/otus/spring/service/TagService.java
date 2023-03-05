package ru.otus.spring.service;

import ru.otus.spring.models.TagFile;

import java.nio.file.Path;

public interface TagService {
    TagFile saveTagToDb(TagFile tagFile);

    void saveTagToFile(TagFile tagFile);

    TagFile loadTagFromFile(Path path);

    void clearTagToFile(String id);

    void deleteByFileName(String fileName);
}
