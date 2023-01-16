package ru.otus.spring.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.models.Author;
import ru.otus.spring.service.AuthorService;

import javax.validation.Valid;

@Controller
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/author/all")
    public String getAllAuthor(Model model) {
        model.addAttribute("authors", service.findAll());
        return "/author/all";
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @GetMapping("/author/edit/{id}")
    public String getAuthorEditPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("author", service.findById(id));
        return "/author/edit";
    }
    @PreAuthorize("hasAuthority('WRITE')")
    @PostMapping("/author/edit/{id}")
    public String saveAuthorEditPage(@Valid @ModelAttribute("author") Author author,
                                     BindingResult bindingResult,
                                     @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "/author/edit";
        }
        author.setId(id);
        service.save(author);
        return "redirect:/author/all";
    }
    @PreAuthorize("hasAuthority('CREATE')")
    @GetMapping("/author/create")
    public String getAuthorCreationPage(@ModelAttribute("author") Author author) {
        return "/author/create";
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/author/create")
    public String saveAuthorCreationPage(@Valid @ModelAttribute("author") Author author,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/author/create";
        }
        Author newAuthor = new Author(author.getAuthorName());
        service.save(newAuthor);
        return "redirect:/author/all";
    }
    @PreAuthorize("hasAuthority('DELETE')")
    @GetMapping("/author/delete/{id}")
    public String getAuthorDeletePage(@PathVariable("id") long id, Model model) {
        model.addAttribute("author", service.findById(id));
        return "/author/delete";
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @PostMapping("/author/delete")
    public String deleteAuthor(@RequestParam("id") long id) {
        service.deleteById(id);
        return "redirect:/author/all";
    }
}
