package ru.otus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.service.GenreService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class GenreController {

    private final GenreService service;

    @Autowired
    protected MutableAclService mutableAclService;
    @Autowired
    private GenreRepository genreRepository;

    public GenreController(GenreService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/genre/all")
    public String getAllGenre(Model model) {
        List<Genre> genres = service.findAll();
        model.addAttribute("genres", genres);
        return "/genre/all";
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @GetMapping("/genre/edit/{id}")
    public String getGenreEditPage(@PathVariable("id") long id, Model model) {
        Genre genre = service.findById(id);
        model.addAttribute("genre", genre);
        return "/genre/edit";
    }

    @PreAuthorize("hasAuthority('WRITE')")
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

    @PreAuthorize("hasAuthority('CREATE')")
    @GetMapping("/genre/create")
    public String getGenreCreationPage(@ModelAttribute("genre") Genre genre) {
        return "/genre/create";
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/genre/create")
    public String saveGenreCreationPage(@Valid @ModelAttribute("genre") Genre genre,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/genre/create";
        }
        Genre newGenre = new Genre(genre.getGenreName());
        service.save(newGenre);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MutableAcl acl = null;

        // Подготовить информацию, которую хотим добавить в систему управления доступом (ACE).
        ObjectIdentity oi = new ObjectIdentityImpl(Genre.class, newGenre.getId());
        Sid sid = new PrincipalSid(authentication.getName());

        // Встроенные разрешения по умолчанию
        Permission administration = BasePermission.ADMINISTRATION;
        Permission create = BasePermission.CREATE;
        Permission delete = BasePermission.DELETE;
        Permission read = BasePermission.READ;
        Permission write = BasePermission.WRITE;

        // Создать или обновите соответствующий ACL
        try {
            acl = (MutableAcl) mutableAclService.readAclById(oi);
        } catch (NotFoundException nfe) {
            acl = mutableAclService.createAcl(oi);
        }

        // Теперь предоставьте некоторые разрешения через запись управления доступом (ACE).
        acl.insertAce(acl.getEntries().size(), read, sid, true);
        mutableAclService.updateAcl(acl);
        return "redirect:/genre/all";
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @GetMapping("/genre/delete/{id}")
    public String getGenreDeletePage(@PathVariable("id") long id, Model model) {
        model.addAttribute("genre", service.findById(id));
        return "/genre/delete";
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @PostMapping("/genre/delete")
    public String deleteGenre(@RequestParam("id") long id) {
        service.deleteById(id);
        return "redirect:/genre/all";
    }
}
