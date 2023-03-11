package ru.otus.spring.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.otus.spring.controller.DownloadController;
import ru.otus.spring.models.Mp3FileDescriptor;
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
                    fileRepository.save(new Mp3FileDescriptor(baseName, baseName, randomFileName, extension, url, size, tag));
                }
            } catch (Exception e) {
                throw new RuntimeException("Не удалось сохранить файл!");
            }
        }
    }

    @Override
    public Mp3FileDescriptor findById(String id) {
        return fileRepository.findById(id).orElseThrow(() -> new RuntimeException("Файла С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
    }

    @Override
    public List<Mp3FileDescriptor> findAll() {
        return fileRepository.findAll();
    }

    @Override
    public void save(Mp3FileDescriptor mp3FileDescriptor) {
        mp3FileDescriptor.setUserName(getCurrentUsername());
        fileRepository.save(mp3FileDescriptor);
    }

    @Override
    public List<Mp3FileDescriptor> saveAll(List<Mp3FileDescriptor> list) {
        return fileRepository.saveAll(list);
    }

    @Override
    public Mp3FileDescriptor findByFileName(String fileName) {
        return fileRepository.findByFileName(fileName);
    }

    @Override
    public List<Mp3FileDescriptor> changePositionFile(String id, String idToStart) {
        Mp3FileDescriptor mp3FileDescriptor = fileRepository.findById(id).orElseThrow(() -> new RuntimeException("Файла С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
        Mp3FileDescriptor mp3FileDescriptorReplace = fileRepository.findById(idToStart).orElseThrow(() -> new RuntimeException("Файла С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
        String serialNumber = mp3FileDescriptor.getSerialNumber();
        mp3FileDescriptor.setSerialNumber(mp3FileDescriptorReplace.getSerialNumber());
        mp3FileDescriptorReplace.setSerialNumber(serialNumber);
        fileRepository.save(mp3FileDescriptorReplace);
        fileRepository.save(mp3FileDescriptor);
        List<Mp3FileDescriptor> list = fileRepository.findAll();
        Collections.sort(list);
        return list;
    }

    @Override
    public List<Mp3FileDescriptor> betweenPositionFile(String id, String idToStart) {
        Mp3FileDescriptor positionMp3FileDescriptor = fileRepository.findById(id).orElseThrow(() -> new RuntimeException("Файла С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
        Mp3FileDescriptor newPositionMp3FileDescriptor = fileRepository.findById(idToStart).orElseThrow(() -> new RuntimeException("Файла С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
        int fromPosition = positionMp3FileDescriptor.getSerialNumberInt();
        int newPosition = newPositionMp3FileDescriptor.getSerialNumberInt();
        List<Mp3FileDescriptor> list = fileRepository.findAll();
        int listSize = list.size();
        String pattern = "%0" + String.valueOf(listSize).length() + "d";
        List<Mp3FileDescriptor> newList = list.stream()
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
        Mp3FileDescriptor mp3FileDescriptor = fileRepository.findById(id).orElseThrow(() -> new RuntimeException("Файла С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
        fileStorageService.delete(mp3FileDescriptor.getFileName());
        tagRepository.deleteById(mp3FileDescriptor.getTag().getId());
        fileRepository.deleteById(id);
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
