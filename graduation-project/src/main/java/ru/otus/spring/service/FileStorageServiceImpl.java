package ru.otus.spring.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import ru.otus.spring.models.File;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path rootLocation = Paths.get("uploads");

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
            Resource resource = new UrlResource(file.toUri());
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
    public StreamingResponseBody loadZip(List<File> fileList) {

//            for (final File file : fileList) {
//                Path path = this.rootLocation.resolve(file.getFileName());
//                try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream()); InputStream inputStream = new FileInputStream(path.toString())) {
//
//                final ZipEntry zipEntry = new ZipEntry(file.getFileName());
//                zipOut.putNextEntry(zipEntry);
//                byte[] bytes=new byte[1024];
//                int length;
//                while ((length=inputStream.read(bytes)) >= 0) {
//                    zipOut.write(bytes, 0, length);
//                }
//                } catch (final IOException e) {
//                }
//            }
//            zipOut.close();
return null;
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
