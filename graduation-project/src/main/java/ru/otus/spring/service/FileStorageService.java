package ru.otus.spring.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import ru.otus.spring.models.Mp3FileDescriptor;

import java.nio.file.Path;
import java.util.List;

public interface FileStorageService {
    void init();

    Path save(MultipartFile file, String randomFileName);

    Resource load(String filename);

    StreamingResponseBody loadZip(List<Mp3FileDescriptor> list);

    void delete(String serverName);

    void deleteAll();
}
