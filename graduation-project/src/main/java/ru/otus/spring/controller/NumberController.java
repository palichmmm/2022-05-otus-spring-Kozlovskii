package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.models.File;
import ru.otus.spring.service.FileService;
import ru.otus.spring.service.NumberService;

import java.util.List;

@Controller
public class NumberController {

    private final FileService fileService;
    private final NumberService numberService;

    public NumberController(FileService fileService, NumberService numberService) {
        this.fileService = fileService;
        this.numberService = numberService;
    }

    @GetMapping("/number/list")
    public String numberListPage(Model model) {
        List<File> fileList = fileService.findAllByUserName();
        numberService.detectAndReplaceNumberFile(fileList);
        numberService.renumbering(fileList);
        fileService.saveAll(fileList);
        model.addAttribute("files", fileList);
        return "number/list";
    }

    @PostMapping("/number/track")
    public String numberTrack(@RequestParam("number") String number,
                              @RequestParam("fileName") String fileName) {
        File file = fileService.findByFileName(fileName);
        File fileReplace = fileService.findBySerialNumberAndUserName(number);
        fileReplace.setSerialNumber(file.getSerialNumber());
        file.setSerialNumber(number);
        fileService.save(fileReplace);
        fileService.save(file);
        return "redirect:/number/list";
    }
}
