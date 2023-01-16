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
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Genre;
import ru.otus.spring.service.AuthorService;

import javax.validation.Valid;

@Controller
public class AuthorController {

    private final AuthorService service;

    @Autowired
    protected MutableAclService mutableAclService;

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MutableAcl acl = null;

        // Подготовить информацию, которую хотим добавить в систему управления доступом (ACE).
        ObjectIdentity oi = new ObjectIdentityImpl(Genre.class, newAuthor.getId());
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
