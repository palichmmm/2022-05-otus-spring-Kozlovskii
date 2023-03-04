package ru.otus.spring.service;

import ru.otus.spring.models.TagFile;

import java.nio.file.Path;

public interface FileTagService {
    TagFile saveTag(Path path);

    TagFile loadTag(Path path);

    void clearTags(String id);
}
