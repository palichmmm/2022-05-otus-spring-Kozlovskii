package ru.otus.spring.service;

import ru.otus.spring.models.File;

import java.util.List;

public interface NumberService {

    List<File> detectAndReplaceNumberFile(List<File> list);

    List<File> renumbering(List<File> list);

}
