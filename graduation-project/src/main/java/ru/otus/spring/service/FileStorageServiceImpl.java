package ru.otus.spring.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.otus.spring.controller.FileController;
import ru.otus.spring.models.File;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileStorageServiceImpl implements FilesStorageService {

    public static final String TEMP_FILE_LABEL = "_temp";
    private final Path rootLocation = Paths.get("./uploads");

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать папку для загрузки!");
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String baseName = FilenameUtils.getBaseName(file.getOriginalFilename());
            if (extension != null && extension.equalsIgnoreCase("mp3")) {
                Files.copy(file.getInputStream(),
                        this.rootLocation.resolve(baseName + TEMP_FILE_LABEL + "." + extension));
            }
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("Файл с таким именем уже существует.");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Не удалось прочитать файл!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public List<File> loadAll() {
        try (Stream<Path> walk = Files.walk(this.rootLocation, 1)) {
            return walk.filter(path -> !path.equals(this.rootLocation)).map(this.rootLocation::relativize)
                    .map(path -> {
                        String filename = path.getFileName().toString();
                        String url = MvcUriComponentsBuilder.fromMethodName(FileController.class,
                                "getFile", path.getFileName().toString()).build().toString();
                        return new File(filename, url);
                    }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить файлы!");
        }
    }
}
