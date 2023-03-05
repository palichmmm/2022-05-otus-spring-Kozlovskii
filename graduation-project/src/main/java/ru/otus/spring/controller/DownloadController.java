package ru.otus.spring.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.spring.service.FileUploadService;

import java.nio.charset.StandardCharsets;

@Controller
public class DownloadController {
    private final FileUploadService uploadService;

    public DownloadController(FileUploadService uploadService) {
        this.uploadService = uploadService;
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource file = uploadService.downloadFile(filename);
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(uploadService.outputFileName(filename), StandardCharsets.UTF_8)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .body(file);
    }

    //    @GetMapping(value = "/download/zip", produces="application/zip")
//    public void zipDownload(@RequestParam List<String> name, HttpServletResponse response) throws IOException {
//        ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
//        for (String fileName : name) {
//            FileSystemResource resource = new FileSystemResource(fileBasePath + fileName);
//            ZipEntry zipEntry = new ZipEntry(resource.getFilename());
//            zipEntry.setSize(resource.contentLength());
//            zipOut.putNextEntry(zipEntry);
//            StreamUtils.copy(resource.getInputStream(), zipOut);
//            zipOut.closeEntry();
//        }
//        zipOut.finish();
//        zipOut.close();
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipFileName + "\"");
//    }
}
