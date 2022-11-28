package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.models.Author;
import ru.otus.spring.service.AuthorService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @GetMapping("/author/all")
    public String all(Model model) {
        List<Author> authors = service.findAll();
        model.addAttribute("authors", authors);
        return "/author/all";
    }

    @GetMapping("/author/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model) {
        Author author = service.findById(id);
        model.addAttribute("author", author);
        return "/author/edit";
    }

    @Validated
    @PostMapping("/author/edit/{id}")
    public String edit(@Valid @ModelAttribute("author") Author author,
                       BindingResult bindingResult,
                       @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "/author/edit";
        }
        author.setId(id);
        service.save(author);
        return "redirect:/author/all";
    }

    @GetMapping("/author/create")
    public String create(@ModelAttribute("author") Author author) {
        return "/author/create";
    }

    @Validated
    @PostMapping("/author/create")
    public String edit(@Valid @ModelAttribute("author") Author author,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/author/create";
        }
        Author newAuthor = new Author(author.getAuthorName());
        service.save(newAuthor);
        return "redirect:/author/all";
    }

    @DeleteMapping("/author/delete/{id}")
    public @ResponseBody String edit(@PathVariable("id") long id) {
        service.deleteById(id);
        return "/author/all";
    }
}
