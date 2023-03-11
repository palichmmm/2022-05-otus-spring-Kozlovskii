package ru.otus.spring.service;

import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.models.File;

import java.util.List;

public interface FileService {

    void uploads(MultipartFile[] files);

    File findById(String id);

    List<File> findAll();

    void save(File file);

    List<File> saveAll(List<File> list);

    File findByFileName(String fileName);

    List<File> changePositionFile(String id, String idToStart);

    List<File> betweenPositionFile(String id, String idToStart);

    void deleteById(String id);
}
