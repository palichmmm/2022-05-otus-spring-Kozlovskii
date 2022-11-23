package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.CommentRepository;

@Controller
public class CommentController {

    private final CommentRepository repository;
    private final BookRepository bookRepository;

    public CommentController(CommentRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/comment/edit/{id}")
    public String edit(@PathVariable long id, Model model) {
        Comment comment = repository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("comment", comment);
        return "/comment/edit";
    }

    @PostMapping("/comment/edit/{id}")
    public String edit(@PathVariable long id, @RequestParam("comment") String comment) {
        Comment newComment = repository.findById(id).orElseThrow(RuntimeException::new);
        newComment.setComment(comment);
        repository.save(newComment);
        return "redirect:/book/comments/" + newComment.getBook().getId();
    }

    @Validated
    @GetMapping("/comment/create/{id}")
    public String create(@ModelAttribute("comment") Comment comment, @PathVariable("id") long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("book", book);
        return "/comment/create";
    }

    @PostMapping("/comment/create/{id}")
    public String create(@PathVariable("id") long id, @RequestParam("comment") String comment) {
        Comment newComment = new Comment(new Book(id), comment);
        repository.save(newComment);
        return "redirect:/book/comments/" + id;
    }

    @DeleteMapping("/comment/delete/{id}")
    public @ResponseBody String edit(@PathVariable("id") long id) {
        Comment comment = repository.findById(id).orElseThrow(RuntimeException::new);
        repository.deleteById(id);
        return "/book/comments/" + comment.getBook().getId();
    }
}