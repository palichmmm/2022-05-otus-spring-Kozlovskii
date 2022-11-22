package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.GenreRepository;

import javax.validation.Valid;
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
    public String edit(@PathVariable("id") long id, Model model) {
        Genre genre = repository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("genre", genre);
        return "/genre/edit";
    }

    @Validated
    @PostMapping("/genre/edit/{id}")
    public String edit(@Valid @ModelAttribute("genre") Genre genre,
                       BindingResult bindingResult,
                       @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "/genre/edit";
        }
        genre.setId(id);
        repository.save(genre);
        return "redirect:/genre/all";
    }

    @GetMapping("/genre/create")
    public String create(@ModelAttribute("genre") Genre genre) {
        return "/genre/create";
    }

    @Validated
    @PostMapping("/genre/create")
    public String edit(@Valid @ModelAttribute("genre") Genre genre,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/genre/create";
        }
        Genre newGenre = new Genre(genre.getGenreName());
        repository.save(newGenre);
        return "redirect:/genre/all";
    }

    @DeleteMapping("/genre/delete/{id}")
    public @ResponseBody String edit(@PathVariable("id") long id) {
        repository.deleteById(id);
        return "/genre/all";
    }
}
