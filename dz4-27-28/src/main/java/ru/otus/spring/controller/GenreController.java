package ru.otus.spring.controller;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.models.Genre;
import ru.otus.spring.service.AclPermissionService;
import ru.otus.spring.service.GenreService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class GenreController {

    private final GenreService service;
    private final AclPermissionService aclPermissionService;

    public GenreController(GenreService service, AclPermissionService aclPermissionService) {
        this.service = service;
        this.aclPermissionService = aclPermissionService;
    }

    @GetMapping("/genre/all")
    public String getAllGenre(Model model) {
        List<Genre> genres = service.findAll();
        model.addAttribute("genres", genres);
        return "/genre/all";
    }

    @GetMapping("/genre/edit/{id}")
    public String getGenreEditPage(@PathVariable("id") long id, Model model) {
        Genre genre = service.findById(id);
        model.addAttribute("genre", genre);
        return "/genre/edit";
    }

    @PostMapping("/genre/edit/{id}")
    public String saveGenreEditPage(@Valid @ModelAttribute("genre") Genre genre,
                                    BindingResult bindingResult,
                                    @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "/genre/edit";
        }
        genre.setId(id);
        service.save(genre);
        return "redirect:/genre/all";
    }

    @GetMapping("/genre/create")
    public String getGenreCreationPage(@ModelAttribute("genre") Genre genre) {
        return "/genre/create";
    }

    @PostMapping("/genre/create")
    public String saveGenreCreationPage(@Valid @ModelAttribute("genre") Genre genre,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/genre/create";
        }
        Genre newGenre = new Genre(genre.getGenreName());
        service.save(newGenre);
        aclPermissionService.savePermission(Genre.class, newGenre.getId(), BasePermission.WRITE);
        return "redirect:/genre/all";
    }

    @GetMapping("/genre/delete/{id}")
    public String getGenreDeletePage(@PathVariable("id") long id, Model model) {
        model.addAttribute("genre", service.findById(id));
        return "/genre/delete";
    }

    @PostMapping("/genre/delete")
    public String deleteGenre(@RequestParam("id") long id) {
        service.deleteById(id);
        return "redirect:/genre/all";
    }
}
