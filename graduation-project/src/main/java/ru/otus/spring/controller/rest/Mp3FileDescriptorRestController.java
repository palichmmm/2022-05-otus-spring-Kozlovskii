package ru.otus.spring.controller.rest;

import org.springframework.web.bind.annotation.*;
import ru.otus.spring.models.Mp3FileDescriptor;
import ru.otus.spring.service.FileService;

import java.util.List;

@RestController
public class Mp3FileDescriptorRestController {
    private final FileService fileService;

    public Mp3FileDescriptorRestController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/api/file")
    public List<Mp3FileDescriptor> getAllFile() {
        return fileService.findAll();
    }
    @DeleteMapping("/api/file/{id}")
    public void deleteById(@PathVariable String id) {
        fileService.deleteById(id);
    }

    @PutMapping("/api/file")
    public List<Mp3FileDescriptor> changePositionFile(@RequestParam("id") String id, @RequestParam("idToStart") String idToStart) {
        return fileService.changePositionFile(id, idToStart);
    }

    @PatchMapping("/api/file")
    public List<Mp3FileDescriptor> betweenPositionFile(@RequestParam("id") String id, @RequestParam("idToStart") String idToStart) {
        return fileService.betweenPositionFile(id, idToStart);
    }
}
