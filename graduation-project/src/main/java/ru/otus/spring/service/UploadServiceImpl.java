package ru.otus.spring.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.otus.spring.controller.DownloadController;
import ru.otus.spring.models.File;
import ru.otus.spring.models.Tag;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {
    private final FileService fileService;
    private final TagService tagService;
    private final FileStorageService fileStorageService;

    public UploadServiceImpl(FileService fileService, TagService tagService, FileStorageService fileStorageService) {
        this.fileService = fileService;
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
                boolean existsFileInDb = fileService.existsByOriginalNameAndUserName(baseName);
                if (extension != null && extension.equalsIgnoreCase("mp3") && !existsFileInDb) {
                    Path path = fileStorageService.save(file, randomFileName);
                    String url = MvcUriComponentsBuilder.fromMethodName(DownloadController.class,
                            "downloadFile", path.getFileName().toString()).build().toString();
                    long size = Files.size(path);
                    Tag tag = tagService.loadTagFromFile(path);
                    if (tag != null) {
                        tagService.saveTagToDb(tag);
                    }
                    fileService.saveToDb(
                            new File(baseName, baseName, randomFileName, extension, url, size, tag));
                }
            } catch (Exception e) {
                throw new RuntimeException("Не удалось сохранить файл!");
            }
        }
    }

    @Override
    public void deleteByFileName(String fileName) {
        fileStorageService.delete(fileName);
        fileService.deleteByFileName(fileName);
        tagService.deleteByFileName(fileName);
    }
}
