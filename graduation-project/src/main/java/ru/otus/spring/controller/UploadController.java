package ru.otus.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.service.FileService;
import ru.otus.spring.service.UploadService;

@Controller
public class UploadController {
    private final UploadService uploadService;
    private final FileService fileService;

    public UploadController(UploadService uploadService, FileService fileService) {
        this.uploadService = uploadService;
        this.fileService = fileService;
    }

    @GetMapping("/upload/form")
    public String uploadForm(Model model) {
        model.addAttribute("files", fileService.findAllByUserName());
        return "upload/form";
    }

    @PostMapping("/upload/form")
    public String uploadFile(@RequestParam("file") MultipartFile[] files, Model model) {
        try {
            uploadService.saveAll(files);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        model.addAttribute("files", fileService.findAllByUserName());
        return "upload/form";
    }

    @DeleteMapping("/upload/form/{fileName:.+}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteFile(@PathVariable String fileName) {
        uploadService.deleteByFileName(fileName);
    }
}
