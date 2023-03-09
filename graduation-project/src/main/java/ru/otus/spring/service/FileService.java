package ru.otus.spring.service;

import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.models.File;

import java.util.List;

public interface FileService {

    void uploads(MultipartFile[] files);

    List<File> findAll();

    void save(File file);

    List<File> saveAll(List<File> list);

    File findByFileName(String fileName);

    File findBySerialNumberAndUserName(String serialNumber);

    List<File> findAllByUserName();

    void deleteById(String id);
}
