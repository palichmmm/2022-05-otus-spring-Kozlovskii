package ru.otus.spring.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.otus.spring.controller.DownloadController;
import ru.otus.spring.models.File;
import ru.otus.spring.models.TagFile;
import ru.otus.spring.repository.FileRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {
    private final FileRepository fileRepository;
    private final TagService tagService;
    private final FileStorageService fileStorageService;

    public UploadServiceImpl(FileRepository fileRepository, TagService tagService, FileStorageService fileStorageService) {
        this.fileRepository = fileRepository;
        this.tagService = tagService;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public void saveAll(MultipartFile[] files) {
        for (MultipartFile file : files) {
            try {
                String baseName = FilenameUtils.getBaseName(file.getOriginalFilename());
                String extension = FilenameUtils.getExtension(file.getOriginalFilename());
                String randomFileName = UUID.randomUUID() + "." + extension;
                boolean existsFileInDb = fileRepository.existsByOriginalNameAndUserName(baseName, getCurrentUsername());
                if (extension != null && extension.equalsIgnoreCase("mp3") && !existsFileInDb) {
                    Path path = fileStorageService.save(file, randomFileName);
                    String url = MvcUriComponentsBuilder.fromMethodName(DownloadController.class,
                            "downloadFile", path.getFileName().toString()).build().toString();
                    long size = Files.size(path);
                    TagFile tagFile = tagService.loadTagFromFile(path);
                    if (tagFile != null) {
                        tagService.saveTagToDb(tagFile);
                    }
                    fileRepository.save(
                            new File(baseName, baseName, randomFileName, extension, getCurrentUsername(),
                                    url, size, tagFile));
                }
            } catch (Exception e) {
                throw new RuntimeException("Не удалось сохранить файл!");
            }
        }
    }

    @Override
    public List<File> findAll() {
        return fileRepository.findAllByUserName(getCurrentUsername());
    }

    @Override
    public void deleteByFileName(String fileName) {
        fileStorageService.delete(fileName);
        fileRepository.deleteByFileName(fileName);
        tagService.deleteByFileName(fileName);
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
