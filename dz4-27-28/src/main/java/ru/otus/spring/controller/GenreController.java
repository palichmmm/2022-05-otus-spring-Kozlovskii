package ru.otus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextChangedEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

        // Подготовьте информацию, которую мы хотели бы добавить в нашу запись управления доступом (ACE).
//        ObjectIdentity oi = new ObjectIdentityImpl(Genre.class, new Long(44));
//        Sid sid = new PrincipalSid("Samantha");
//        Permission p = BasePermission.ADMINISTRATION;

// Создайте или обновите соответствующий ACL
//        MutableAcl acl = null;
//        try {
//            acl = (MutableAcl) mutableAclService.readAclById(oi);
//        } catch (NotFoundException nfe) {
//            acl = mutableAclService.createAcl(oi);
//        }

// Теперь предоставьте некоторые разрешения через запись управления доступом (ACE).
//        acl.insertAce(acl.getEntries().length, p, sid, true);
//        aclService.updateAcl(acl);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication==="+authentication);
        final Sid owner = new PrincipalSid( authentication );
        System.out.println("PrincipalSid==="+owner);
        MutableAcl acl = null;
        for (Genre genre : genres) {

            // Подготовьте информацию, которую мы хотели бы добавить в нашу запись управления доступом (ACE).
            ObjectIdentity oi = new ObjectIdentityImpl( Genre.class, genre.getId() );
            Sid sid = new PrincipalSid(authentication.getName());
            Permission p = BasePermission.ADMINISTRATION;
            Permission pp = BasePermission.CREATE;
            Permission ppp = BasePermission.DELETE;
            Permission pppp = BasePermission.READ;
            Permission ppppp = BasePermission.WRITE;

            // Создайте или обновите соответствующий ACL
            try {
                acl = (MutableAcl) mutableAclService.readAclById(oi);
            } catch (NotFoundException nfe) {
                acl = mutableAclService.createAcl(oi);
            }

            // Теперь предоставьте некоторые разрешения через запись управления доступом (ACE).
            acl.insertAce(acl.getEntries().size(), p, sid, true);
            mutableAclService.updateAcl(acl);

            System.out.println("ObjectIdentityImpl===" + oi);
//            MutableAcl acl = mutableAclService.createAcl( oi );
//            System.out.println("+++==="+acl);
        }

//        Sid admin = new GrantedAuthoritySid("BATCH");
//        System.out.println("GrantedAuthoritySid===" + admin);

//        acl.setOwner( owner );
//        acl.insertAce( acl.getEntries().size(), BasePermission.ADMINISTRATION, admin, true );
//        System.out.println("+++==="+acl);
//        mutableAclService.updateAcl( acl );
//        System.out.println("+++==="+acl);


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
