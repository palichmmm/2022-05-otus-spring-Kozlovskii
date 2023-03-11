package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.models.Mp3FileDescriptor;
import ru.otus.spring.service.FileService;
import ru.otus.spring.service.FormatService;

import java.util.Collections;
import java.util.List;

@Controller
public class FormatController {

    private final FileService fileService;
    private final FormatService formatService;

    public FormatController(FileService fileService, FormatService formatService) {
        this.fileService = fileService;
        this.formatService = formatService;
    }

    @GetMapping("/format/form")
    public String formatPage(Model model) {
        List<Mp3FileDescriptor> list = fileService.findAll();
        Collections.sort(list);
        model.addAttribute("files", list);
        return "format/form";
    }

    @GetMapping("/format/uppercase")
    public String formatUppercase(Model model) {
        List<Mp3FileDescriptor> mp3FileDescriptorList = formatService.uppercaseNameFile(fileService.findAll());
        model.addAttribute("files", fileService.saveAll(mp3FileDescriptorList));
        return "format/form";
    }

    @GetMapping("/format/camelcase")
    public String formatCamelcase(Model model) {
        List<Mp3FileDescriptor> mp3FileDescriptorList = formatService.camelcaseNameFile(fileService.findAll());
        model.addAttribute("files", fileService.saveAll(mp3FileDescriptorList));
        return "format/form";
    }

    @GetMapping("/format/lowercase")
    public String formatLowercase(Model model) {
        List<Mp3FileDescriptor> mp3FileDescriptorList = formatService.lowercaseNameFile(fileService.findAll());
        model.addAttribute("files", fileService.saveAll(mp3FileDescriptorList));
        return "format/form";
    }

    @PostMapping("/format/form")
    public String changeTextNameFile(@ModelAttribute("text") String text,
                                     @ModelAttribute("changeText") String changeText,
                                     BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("files", fileService.findAll());
            return "format/form";
        }
        List<Mp3FileDescriptor> mp3FileDescriptorList = formatService.changeTextNameFile(text, changeText, fileService.findAll());
        model.addAttribute("files", fileService.saveAll(mp3FileDescriptorList));
        return "format/form";
    }

}
