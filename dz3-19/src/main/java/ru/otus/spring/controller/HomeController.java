package ru.otus.spring.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    public HomeController() {
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/author")
    public String author() {
        return "author";
    }

    @GetMapping("/genre")
    public String genre() {
        return "genre";
    }

    @GetMapping("/book")
    public String book() {
        return "book";
    }

    @GetMapping("/comment/book/{id}")
    public String comment(@PathVariable("id") long id, Model model) {
        model.addAttribute("id", id);
        return "comment";
    }
}
