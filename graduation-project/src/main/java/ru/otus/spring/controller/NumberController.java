package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.spring.models.Mp3FileDescriptor;
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
        List<Mp3FileDescriptor> mp3FileDescriptorList = fileService.findAll();
        numberService.detectAndReplaceNumberFile(mp3FileDescriptorList);
        numberService.renumbering(mp3FileDescriptorList);
        fileService.saveAll(mp3FileDescriptorList);
        model.addAttribute("files", mp3FileDescriptorList);
        return "number/list";
    }

//    @PostMapping("/number/track")
//    public String numberTrack(@RequestParam("id") String id,
//                              @RequestParam("idToStart") String idToStart) {
//        Mp3FileDescriptor file = fileService.findById(id);
//        Mp3FileDescriptor fileReplace = fileService.findById(idToStart);
//        String serialNumber = file.getSerialNumber();
//        file.setSerialNumber(fileReplace.getSerialNumber());
//        fileReplace.setSerialNumber(serialNumber);
//        fileService.save(fileReplace);
//        fileService.save(file);
//        return "redirect:/number/list";
//    }
}
