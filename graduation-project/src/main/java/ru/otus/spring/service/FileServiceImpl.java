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
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public File findById(String id) {
        return fileRepository.findById(id).orElseThrow(() -> new RuntimeException("Файла С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
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
    public List<File> changePositionFile(String id, String idToStart) {
        File file = fileRepository.findById(id).orElseThrow(() -> new RuntimeException("Файла С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
        File fileReplace = fileRepository.findById(idToStart).orElseThrow(() -> new RuntimeException("Файла С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
        String serialNumber = file.getSerialNumber();
        file.setSerialNumber(fileReplace.getSerialNumber());
        fileReplace.setSerialNumber(serialNumber);
        fileRepository.save(fileReplace);
        fileRepository.save(file);
        List<File> list = fileRepository.findAll();
        Collections.sort(list);
        return list;
    }

    @Override
    public List<File> betweenPositionFile(String id, String idToStart) {
        File positionFile = fileRepository.findById(id).orElseThrow(() -> new RuntimeException("Файла С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
        File newPositionFile = fileRepository.findById(idToStart).orElseThrow(() -> new RuntimeException("Файла С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
        int fromPosition = positionFile.getSerialNumberInt();
        int newPosition = newPositionFile.getSerialNumberInt();
        List<File> list = fileRepository.findAll();
        int listSize = list.size();
        String pattern = "%0" + String.valueOf(listSize).length() + "d";
        List<File> newList = list.stream()
                .peek(file -> {
                    int number = file.getSerialNumberInt();
                    if (number < fromPosition && number < newPosition) {
                        return;
                    }
                    if (number == fromPosition) {
                        file.setSerialNumber(String.format(pattern, newPosition));
                        return;
                    }
                    if (fromPosition < newPosition) {
                        if (number <= newPosition) {
                            file.setSerialNumber(String.format(pattern, file.getSerialNumberInt() - 1));
                        }
                    } else {
                        if (number < fromPosition) {
                            file.setSerialNumber(String.format(pattern, file.getSerialNumberInt() + 1));
                        }
                    }
                }).sorted().collect(Collectors.toList());
        fileRepository.saveAll(newList);
        return newList;
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
