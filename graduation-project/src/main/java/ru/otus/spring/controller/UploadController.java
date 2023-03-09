package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.service.FileService;

@Controller
public class UploadController {
    private final FileService fileService;

    public UploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/upload/form")
    public String uploadForm(Model model) {
        model.addAttribute("files", fileService.findAll());
        return "upload/form";
    }

    @PostMapping("/upload/form")
    public String uploadFile(@RequestParam("file") MultipartFile[] files, Model model) {
        try {
            fileService.uploads(files);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        model.addAttribute("files", fileService.findAll());
        return "upload/form";
    }
}
