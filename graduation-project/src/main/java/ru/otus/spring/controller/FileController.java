package ru.otus.spring.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.service.FilesStorageService;

@Controller
public class FileController {
    private final FilesStorageService filesStorageService;

    public FileController(FilesStorageService filesStorageService) {
        this.filesStorageService = filesStorageService;
    }

    @GetMapping("/files/new")
    public String newFile(Model model) {
        return "upload_form";
    }

    @PostMapping("/files/upload")
    public String uploadFile(Model model, @RequestParam("file") MultipartFile[] files) {
        String message = "";
        for (MultipartFile file : files) {
            try {
                filesStorageService.save(file);

                message = "Загружен файл: " + file.getOriginalFilename();
                model.addAttribute("message", message);
            } catch (Exception e) {
                message = "Не удалось загрузить файл: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
                model.addAttribute("message", message);
            }
        }
        return "files";
    }

    @GetMapping("/files")
    public String getListFiles(Model model) {
        model.addAttribute("files", filesStorageService.loadAll());
        return "files";
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = filesStorageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
