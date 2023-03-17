package ru.otus.spring.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import ru.otus.spring.service.DownloadService;

import java.nio.charset.StandardCharsets;
import java.util.zip.ZipOutputStream;

@Controller
public class DownloadController {
    private final DownloadService downloadService;


    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(downloadService.outputFileName(filename), StandardCharsets.UTF_8)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .body(downloadService.downloadFile(filename));
    }

    @GetMapping(value = "/download/zip", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<StreamingResponseBody> download2() {
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename("mp3collection.zip", StandardCharsets.UTF_8)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .body(outputStream -> {
                    try (ZipOutputStream zipOut = new ZipOutputStream(outputStream)) {
                        downloadService.createZipStream(zipOut);
                    }
                });
    }
}
