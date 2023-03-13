package ru.otus.spring.controller.rest;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.models.Mp3FileDescriptor;
import ru.otus.spring.service.Mp3FileDescriptorPositionService;

import java.util.List;

@RestController
public class Mp3FileDescriptorPositionListRestController {
    private final Mp3FileDescriptorPositionService positionService;

    public Mp3FileDescriptorPositionListRestController(Mp3FileDescriptorPositionService positionService) {
        this.positionService = positionService;
    }

    @PatchMapping("/api/position/change")
    public List<Mp3FileDescriptor> changePositionMp3FileDescriptor(@RequestParam("id1") String id1, @RequestParam("id2") String id2) {
        return positionService.changePositionMp3FileDescriptor(id1, id2);
    }

    @PatchMapping("/api/position/between")
    public List<Mp3FileDescriptor> betweenPositionMp3FileDescriptor(@RequestParam("id") String id, @RequestParam("newIdPosition") String newIdPosition) {
        return positionService.betweenPositionMp3FileDescriptor(id, newIdPosition);
    }

    @PatchMapping("/api/position/up")
    public List<Mp3FileDescriptor> upPositionMp3FileDescriptor(@RequestParam("id") String id) {
        return positionService.upPositionMp3FileDescriptor(id);
    }

    @PatchMapping("/api/position/down")
    public List<Mp3FileDescriptor> downPositionMp3FileDescriptor(@RequestParam("id") String id) {
        return positionService.downPositionMp3FileDescriptor(id);
    }

    @PatchMapping("/api/position/random")
    public List<Mp3FileDescriptor> randomPositionMp3FileDescriptor() {
        return positionService.randomPositionMp3FileDescriptor();
    }
}
