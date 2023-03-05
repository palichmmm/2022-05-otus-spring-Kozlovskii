package ru.otus.spring.service;

import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.spring.models.File;
import ru.otus.spring.repository.FileRepository;

@Service
public class DownloadServiceImpl implements DownloadService {
    private final FileRepository fileRepository;
    private final FileStorageService fileStorageService;

    public DownloadServiceImpl(FileRepository fileRepository, FileStorageService fileStorageService) {
        this.fileRepository = fileRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public Resource downloadFile(String fileName) {
        return fileStorageService.load(fileName);
    }

    @Override
    public Resource downloadAllFileZip() {
        return null;
    }

    @Override
    public String outputFileName(String realFileName) {
        File file = fileRepository.findByFileName(realFileName);
        return (file.getSerialNumber() != null) ? file.getSerialNumber() : "" + file.getOutputName() + "." + file.getExtension();
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
