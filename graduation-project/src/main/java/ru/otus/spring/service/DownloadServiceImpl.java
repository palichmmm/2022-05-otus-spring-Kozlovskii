package ru.otus.spring.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.spring.models.Mp3FileDescriptor;

import java.util.List;

@Service
public class DownloadServiceImpl implements DownloadService {
    private final FileService fileService;
    private final FileStorageService fileStorageService;

    public DownloadServiceImpl(FileService fileService, FileStorageService fileStorageService) {
        this.fileService = fileService;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public Resource downloadFile(String fileName) {
        return fileStorageService.load(fileName);
    }

    @Override
    public Resource downloadAllFileZip() {
        List<Mp3FileDescriptor> list = fileService.findAll();
        return null;
    }

    @Override
    public String outputFileName(String realFileName) {
        return fileService.findByFileName(realFileName).getOutputNumberedFileName();
    }
}
