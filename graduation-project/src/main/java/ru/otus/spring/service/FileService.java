package ru.otus.spring.service;

import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.models.Mp3FileDescriptor;

import java.util.List;

public interface FileService {

    void uploads(MultipartFile[] files);

    Mp3FileDescriptor findById(String id);

    List<Mp3FileDescriptor> findAll();

    void save(Mp3FileDescriptor mp3FileDescriptor);

    List<Mp3FileDescriptor> saveAll(List<Mp3FileDescriptor> list);

    Mp3FileDescriptor findByFileName(String fileName);

    List<Mp3FileDescriptor> changePositionFile(String id, String idToStart);

    List<Mp3FileDescriptor> betweenPositionFile(String id, String idToStart);

    void deleteById(String id);
}
