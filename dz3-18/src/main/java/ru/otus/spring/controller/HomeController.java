package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.GenreRepository;

@Controller
public class HomeController {

    private final AuthorRepository author;
    private final GenreRepository genre;
    private final BookRepository book;

    public HomeController(AuthorRepository author, GenreRepository genre, BookRepository book) {
        this.author = author;
        this.genre = genre;
        this.book = book;
    }

    @GetMapping("/")
    public String home(Model model) {
        long authorCount = author.count();
        long genreCount = genre.count();
        long bookCount = book.count();
        model.addAttribute("authorCount", authorCount);
        model.addAttribute("genreCount", genreCount);
        model.addAttribute("bookCount", bookCount);
        return "home";
    }
}
