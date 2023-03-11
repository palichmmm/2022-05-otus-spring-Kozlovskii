package ru.otus.spring.controller.rest;

import org.springframework.web.bind.annotation.*;
import ru.otus.spring.models.File;
import ru.otus.spring.service.FileService;

import java.util.Collections;
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

    @PutMapping("/api/file")
    public List<File> changePositionFile(@RequestParam("id") String id, @RequestParam("idToStart") String idToStart) {
        return fileService.changePositionFile(id, idToStart);
    }

    @PatchMapping("/api/file")
    public List<File> betweenPositionFile(@RequestParam("id") String id, @RequestParam("idToStart") String idToStart) {
        return fileService.betweenPositionFile(id, idToStart);
    }
}
