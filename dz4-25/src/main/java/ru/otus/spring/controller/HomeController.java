package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;

@Controller
public class HomeController {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;

    public HomeController(AuthorService authorService, GenreService genreService, BookService bookService) {
        this.authorService = authorService;
        this.genreService = genreService;
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("authorCount", authorService.count());
        model.addAttribute("genreCount", genreService.count());
        model.addAttribute("bookCount", bookService.count());
        return "home";
    }

    @GetMapping("/login")
    public String formLogin() {
        return "login";
    }
}
