package ru.otus.string.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    public HomeController() {
    }

//    @GetMapping("/")
//    public String home() {
//        return "home";
//    return "redirect:/files";
//    }

    @GetMapping("/login")
    public String formLogin() {
        return "login";
    }
}
