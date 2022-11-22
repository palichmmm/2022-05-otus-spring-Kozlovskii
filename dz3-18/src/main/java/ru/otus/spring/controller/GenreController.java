package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.GenreRepository;

import java.util.List;

@Controller
public class GenreController {

    private final GenreRepository repository;

    public GenreController(GenreRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/genre/all")
    public String all(Model model) {
        List<Genre> genres = repository.findAll();
        model.addAttribute("genres", genres);
        return "/genre/all";
    }
    @GetMapping("/genre/edit/{id}")
    public String one(@PathVariable("id") long id, Model model) {
        Genre genre = repository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("genre", genre);
        return "/genre/edit";
    }
    @PostMapping("/genre/edit/{id}")
    public String edit(@PathVariable("id") long id, @RequestParam("name") String name, Model model) {
        Genre genre = repository.findById(id).orElseThrow(RuntimeException::new);
        genre.setGenreName(name);
        repository.save(genre);
        return "redirect:/genre/all";
    }
    @GetMapping("/genre/create")
    public String create() {
        return "/genre/create";
    }

    @PostMapping("/genre/create")
    public String edit(@RequestParam("name") String name) {
        Genre genre = new Genre(name);
        repository.save(genre);
        return "redirect:/genre/all";
    }
    @DeleteMapping("/genre/delete/{id}")
    public @ResponseBody String edit(@PathVariable("id") long id) {
        repository.deleteById(id);
        return "/genre/all";
    }
}
