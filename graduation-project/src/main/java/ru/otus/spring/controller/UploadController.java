package ru.otus.spring.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.service.FileUploadService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class UploadController {
    private final FileUploadService uploadService;

    public UploadController(FileUploadService uploadService) {
        this.uploadService = uploadService;
    }

    @GetMapping("/upload/form")
    public String uploadForm(Model model) {
        model.addAttribute("files", uploadService.findAll());
        return "upload/form";
    }

    @PostMapping("/upload/form")
    public String uploadFile(@RequestParam("file") MultipartFile[] files, Model model) {
        try {
            uploadService.saveAll(files);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        model.addAttribute("files", uploadService.findAll());
        return "upload/form";
    }

    @DeleteMapping("/upload/form/{fileName}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteFile(@PathVariable String fileName) {
        uploadService.deleteByFileName(fileName);
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource file = uploadService.downloadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
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
