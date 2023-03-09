package ru.otus.spring.service;

import ru.otus.spring.models.File;

import java.util.List;

public interface FormatService {
    List<File> uppercaseNameFile(List<File> fileList);
    List<File> camelcaseNameFile(List<File> fileList);
    List<File> lowercaseNameFile(List<File> fileList);
    List<File> changeTextNameFile(String text, String changeText, List<File> fileList);
}
