package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.models.File;
import ru.otus.spring.service.FileService;
import ru.otus.spring.service.FormatService;

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
        model.addAttribute("files", fileService.findAllByUserName());
        return "format/form";
    }

    @GetMapping("/format/uppercase")
    public String formatUppercase(Model model) {
        List<File> fileList = formatService.uppercaseNameFile(fileService.findAllByUserName());
        model.addAttribute("files", fileService.saveAll(fileList));
        return "format/form";
    }

    @GetMapping("/format/camelcase")
    public String formatCamelcase(Model model) {
        List<File> fileList = formatService.camelcaseNameFile(fileService.findAllByUserName());
        model.addAttribute("files", fileService.saveAll(fileList));
        return "format/form";
    }

    @GetMapping("/format/lowercase")
    public String formatLowercase(Model model) {
        List<File> fileList = formatService.lowercaseNameFile(fileService.findAllByUserName());
        model.addAttribute("files", fileService.saveAll(fileList));
        return "format/form";
    }

    @PostMapping("/format/form")
    public String changeTextNameFile(@ModelAttribute("text") String text,
                                     @ModelAttribute("changeText") String changeText,
                                     BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("files", fileService.findAllByUserName());
            return "format/form";
        }
        List<File> fileList = formatService.changeTextNameFile(text, changeText, fileService.findAllByUserName());
        model.addAttribute("files", fileService.saveAll(fileList));
        return "format/form";
    }

}
