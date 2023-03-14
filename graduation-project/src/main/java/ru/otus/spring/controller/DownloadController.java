package ru.otus.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.otus.spring.models.Mp3FileDescriptor;
import ru.otus.spring.service.DownloadService;
import ru.otus.spring.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class DownloadController {
    private final Path rootLocation = Paths.get("uploads");
    private final DownloadService downloadService;
    private final FileService fileService;

    private final Logger logger = LoggerFactory.getLogger(DownloadController.class);

    public DownloadController(DownloadService downloadService, FileService fileService) {
        this.downloadService = downloadService;
        this.fileService = fileService;
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource file = downloadService.downloadFile(filename);
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(downloadService.outputFileName(filename), StandardCharsets.UTF_8)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .body(file);
    }

    @GetMapping(value = "/download/zip", produces="application/zip")
    public void download(HttpServletResponse response) throws IOException {
        List<Mp3FileDescriptor> list = fileService.findAll();
        List<String> name = list.stream().map(Mp3FileDescriptor::getFileName).collect(Collectors.toList());
//        Path rootLocation = Paths.get("./uploads");

        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            Resource resource = null;
            for (String fileName : name) {
                Path file = rootLocation.resolve(fileName);
                resource = new UrlResource(file.toUri());
//            FileSystemResource resource = new FileSystemResource(rootLocation + fileName);
                ZipEntry zipEntry = new ZipEntry(Objects.requireNonNull(resource.getFilename()));
                zipEntry.setSize(resource.contentLength());
                zipOut.putNextEntry(zipEntry);

                zipOut.closeEntry();
            }
            zipOut.finish();
            FileInputStream inputStream = new FileInputStream(zipOut.toString());
            StreamUtils.copy(inputStream, zipOut);
            inputStream.close();

        }
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename("collect.zip", StandardCharsets.UTF_8)
                .build();

        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
    }
}
