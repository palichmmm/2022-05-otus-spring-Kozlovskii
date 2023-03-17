package ru.otus.spring.service;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import ru.otus.spring.models.Mp3FileDescriptor;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class DownloadServiceImpl implements DownloadService {
    private final Path rootLocation = Paths.get("uploads");
    private final FileService fileService;

    public DownloadServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public Resource downloadFile(String fileName) {
        try {
            Path file = rootLocation.resolve(fileName);
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
    public void createZipStream(ZipOutputStream zipOut) {
        List<Mp3FileDescriptor> listDescriptor = fileService.findAll();
        for (Mp3FileDescriptor fileDescriptor : listDescriptor) {
            try (FileInputStream fileInputStream = new FileInputStream(rootLocation + "/" + fileDescriptor.getFileName())) {
                ZipEntry zipEntry = new ZipEntry(fileDescriptor.getOutputNumberedFileName());
                zipOut.putNextEntry(zipEntry);
                IOUtils.copy(fileInputStream, zipOut);
                zipOut.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public String outputFileName(String realFileName) {
        return fileService.findByFileName(realFileName).getOutputNumberedFileName();
    }
}
