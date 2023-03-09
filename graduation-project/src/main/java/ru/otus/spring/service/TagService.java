package ru.otus.spring.service;

import ru.otus.spring.models.Tag;

import java.nio.file.Path;

public interface TagService {

    Tag loadTagFromFile(Path path);
}
