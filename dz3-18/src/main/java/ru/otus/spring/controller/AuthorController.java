package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.models.Author;
import ru.otus.spring.repositories.AuthorRepository;

import java.util.List;

@Controller
public class AuthorController {

    private final AuthorRepository repository;

    public AuthorController(AuthorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/author/all")
    public String all(Model model) {
        List<Author> authors = repository.findAll();
        model.addAttribute("authors", authors);
        return "/author/all";
    }

    @GetMapping("/author/edit/{id}")
    public String one(@PathVariable("id") long id, Model model) {
        Author author = repository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("author", author);
        return "/author/edit";
    }

    @PostMapping("/author/edit/{id}")
    public String edit(@PathVariable("id") long id, @RequestParam("name") String name) {
        Author author = repository.findById(id).orElseThrow(RuntimeException::new);
        author.setAuthorName(name);
        repository.save(author);
        return "redirect:/author/all";
    }
    @GetMapping("/author/create")
    public String create() {
        return "/author/create";
    }

    @PostMapping("/author/create")
    public String edit(@RequestParam("name") String name) {
        Author author = new Author(name);
        repository.save(author);
        return "redirect:/author/all";
    }

    @DeleteMapping("/author/delete/{id}")
    public @ResponseBody String edit(@PathVariable("id") long id) {
        repository.deleteById(id);
        return "/author/all";
    }
}
