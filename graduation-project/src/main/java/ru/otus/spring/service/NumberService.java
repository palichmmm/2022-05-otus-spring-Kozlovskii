package ru.otus.spring.service;

import ru.otus.spring.models.File;

import java.util.List;

public interface NumberService {

    List<File> detectAndReplaceNumberFile(List<File> fileList);
    boolean isNumber(String str);
}
