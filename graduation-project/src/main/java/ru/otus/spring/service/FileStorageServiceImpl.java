package ru.otus.spring.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class FileStorageServiceImpl implements FileStorageService {

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
    public Path save(MultipartFile file, String randomFileName) {
        Path path = this.rootLocation.resolve(randomFileName);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            return path;
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить файл!");
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Path file1 = rootLocation.resolve("mmm.mp3");
            Files.copy(file, file1, StandardCopyOption.REPLACE_EXISTING);
            Resource resource = new UrlResource(file1.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Не удалось прочитать файл!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource loadZip(String filename) {
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
    public void delete(String fileName) {
        try {
            Path file = rootLocation.resolve(fileName);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
