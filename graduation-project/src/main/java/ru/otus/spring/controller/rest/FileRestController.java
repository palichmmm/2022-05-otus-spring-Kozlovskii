package ru.otus.spring.controller.rest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.models.File;
import ru.otus.spring.service.FileService;

import java.util.List;

@RestController
public class FileRestController {
    private final FileService fileService;

    public FileRestController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/api/file")
    public List<File> getAllFile() {
        return fileService.findAll();
    }
    @DeleteMapping("/api/file/{id}")
    public void deleteById(@PathVariable String id) {
        fileService.deleteById(id);
    }
}
