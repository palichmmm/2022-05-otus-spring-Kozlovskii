package ru.otus.spring.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    void saveAll(MultipartFile[] files);

    void deleteByFileName(String serverName);
}
