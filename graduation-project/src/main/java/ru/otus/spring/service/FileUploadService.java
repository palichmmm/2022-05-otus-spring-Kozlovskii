package ru.otus.spring.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.models.File;

import java.util.List;

public interface FileUploadService {

    void saveAll(MultipartFile[] files);

    List<File> findAll();

    void deleteByFileName(String serverName);

    Resource downloadFile(String filename);

    String outputFileName(String fileName);
}
