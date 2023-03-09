package ru.otus.spring.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.otus.spring.controller.DownloadController;
import ru.otus.spring.models.File;
import ru.otus.spring.models.Tag;
import ru.otus.spring.repository.FileRepository;
import ru.otus.spring.repository.TagRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final TagRepository tagRepository;
    private final TagService tagService;
    private final FileStorageService fileStorageService;

    public FileServiceImpl(FileRepository fileRepository, TagRepository tagRepository, TagService tagService, FileStorageService fileStorageService) {
        this.fileRepository = fileRepository;
        this.tagRepository = tagRepository;
        this.tagService = tagService;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public void uploads(MultipartFile[] files) {
        for (MultipartFile file : files) {
            try {
                String baseName = FilenameUtils.getBaseName(file.getOriginalFilename());
                String extension = FilenameUtils.getExtension(file.getOriginalFilename());
                String randomFileName = UUID.randomUUID() + "." + extension;
                boolean existsFileInDb = fileRepository.existsByOriginalName(baseName);
                if (extension != null && extension.equalsIgnoreCase("mp3") && !existsFileInDb) {
                    Path path = fileStorageService.save(file, randomFileName);
                    String url = MvcUriComponentsBuilder.fromMethodName(DownloadController.class,
                            "downloadFile", path.getFileName().toString()).build().toString();
                    long size = Files.size(path);
                    Tag tag = tagService.loadTagFromFile(path);
                    if (tag != null) {
                        tagRepository.save(tag);
                    }
                    fileRepository.save(new File(baseName, baseName, randomFileName, extension, url, size, tag));
                }
            } catch (Exception e) {
                throw new RuntimeException("Не удалось сохранить файл!");
            }
        }
    }

    @Override
    public List<File> findAll() {
        return fileRepository.findAll();
    }

    @Override
    public void save(File file) {
        file.setUserName(getCurrentUsername());
        fileRepository.save(file);
    }

    @Override
    public List<File> saveAll(List<File> list) {
        return fileRepository.saveAll(list);
    }

    @Override
    public File findByFileName(String fileName) {
        return fileRepository.findByFileName(fileName);
    }

    @Override
    public File findBySerialNumberAndUserName(String serialNumber) {
        return fileRepository.findBySerialNumberAndUserName(serialNumber, getCurrentUsername());
    }

    @Override
    public List<File> findAllByUserName() {
        return fileRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        File file = fileRepository.findById(id).orElseThrow(() -> new RuntimeException("Файла С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
        fileStorageService.delete(file.getFileName());
        tagRepository.deleteById(file.getTag().getId());
        fileRepository.deleteById(id);
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
