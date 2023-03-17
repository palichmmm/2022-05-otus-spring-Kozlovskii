package ru.otus.spring.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.otus.spring.controller.DownloadController;
import ru.otus.spring.models.Mp3FileDescriptor;
import ru.otus.spring.models.Tag;
import ru.otus.spring.repository.Mp3FileDescriptorRepository;
import ru.otus.spring.repository.TagRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {
    private final Mp3FileDescriptorRepository mp3FileDescriptorRepository;
    private final TagRepository tagRepository;
    private final TagService tagService;
    private final FileStorageService fileStorageService;

    public FileServiceImpl(Mp3FileDescriptorRepository mp3FileDescriptorRepository, TagRepository tagRepository, TagService tagService, FileStorageService fileStorageService) {
        this.mp3FileDescriptorRepository = mp3FileDescriptorRepository;
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
                boolean existsFileInDb = mp3FileDescriptorRepository.existsByOriginalName(baseName);
                if (extension != null && extension.equalsIgnoreCase("mp3") && !existsFileInDb) {
                    Path path = fileStorageService.save(file, randomFileName);
                    String url = MvcUriComponentsBuilder.fromMethodName(DownloadController.class,
                            "downloadFile", path.getFileName().toString()).build().toString();
                    long size = Files.size(path);
                    Tag tag = tagService.loadTagFromFile(path);
                    if (tag != null) {
                        tagRepository.save(tag);
                    }
                    mp3FileDescriptorRepository.save(new Mp3FileDescriptor(baseName, baseName, randomFileName, extension, url, size, tag));
                }
            } catch (Exception e) {
                throw new RuntimeException("Не удалось сохранить файл!");
            }
        }
    }

    @Override
    public Mp3FileDescriptor findById(String id) {
        return mp3FileDescriptorRepository.findById(id).orElseThrow(() -> new RuntimeException("Файла С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
    }

    @Override
    public List<Mp3FileDescriptor> findAll() {
        return mp3FileDescriptorRepository.findAll();
    }

    @Override
    public void save(Mp3FileDescriptor mp3FileDescriptor) {
        mp3FileDescriptor.setUserName(getCurrentUsername());
        mp3FileDescriptorRepository.save(mp3FileDescriptor);
    }

    @Override
    public List<Mp3FileDescriptor> saveAll(List<Mp3FileDescriptor> list) {
        return mp3FileDescriptorRepository.saveAll(list);
    }

    @Override
    public Mp3FileDescriptor findByFileName(String fileName) {
        return mp3FileDescriptorRepository.findByFileName(fileName);
    }

    @Override
    public void deleteById(String id) {
        Mp3FileDescriptor mp3FileDescriptor = mp3FileDescriptorRepository.findById(id).orElseThrow(() -> new RuntimeException("Файла С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
        fileStorageService.delete(mp3FileDescriptor.getFileName());
        tagRepository.deleteById(mp3FileDescriptor.getTag().getId());
        mp3FileDescriptorRepository.deleteById(id);
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
