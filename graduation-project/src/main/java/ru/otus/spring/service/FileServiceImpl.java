package ru.otus.spring.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.spring.models.File;
import ru.otus.spring.repository.FileRepository;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public File saveToDb(File file) {
        file.setUserName(getCurrentUsername());
        return fileRepository.save(file);
    }

    @Override
    public File findByFileNameAndUserName(String fileName) {
        return fileRepository.findByFileNameAndUserName(fileName, getCurrentUsername());
    }

    @Override
    public List<File> findAllByUserName() {
        return fileRepository.findAllByUserName(getCurrentUsername());
    }

    @Override
    public boolean existsByOriginalNameAndUserName(String originalName) {
        return fileRepository.existsByOriginalNameAndUserName(originalName, getCurrentUsername());
    }

    @Override
    public void deleteByFileName(String fileName) {
        fileRepository.deleteByFileName(fileName);
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
