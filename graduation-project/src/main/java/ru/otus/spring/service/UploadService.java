package ru.otus.spring.service;

import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.models.File;

import java.util.List;

public interface UploadService {

    void saveAll(MultipartFile[] files);

    List<File> findAll();

    void deleteByFileName(String serverName);
}
