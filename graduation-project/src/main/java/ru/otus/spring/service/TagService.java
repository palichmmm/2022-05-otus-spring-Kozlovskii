package ru.otus.spring.service;

import ru.otus.spring.models.Tag;

import java.nio.file.Path;

public interface TagService {
    Tag saveTagToDb(Tag tag);

    void saveTagToFile(Tag tag);

    Tag loadTagFromFile(Path path);

    void clearTagToFile(String id);

    void deleteByFileName(String fileName);
}
