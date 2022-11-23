package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.CommentDTO;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.CommentRepository;

import javax.validation.Valid;

@Controller
public class CommentController {

    private final CommentRepository repository;
    private final BookRepository bookRepository;

    public CommentController(CommentRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/comment/edit/{id}")
    public String edit(@ModelAttribute("comment") CommentDTO comment) {
        Comment editComment = repository.findById(comment.getId()).orElseThrow(RuntimeException::new);
        comment.setComment(editComment.getComment());
        comment.setBookId(editComment.getBook().getId());
        comment.setBookName(editComment.getBook().getBookName());
        return "/comment/edit";
    }

    @Validated
    @PostMapping("/comment/edit/{id}")
    public String edit(@Valid @ModelAttribute("comment") CommentDTO comment,
                       BindingResult bindingResult) {
        Comment editComment = repository.findById(comment.getId()).orElseThrow(RuntimeException::new);
        if (bindingResult.hasErrors()) {
            comment.setBookId(editComment.getBook().getId());
            comment.setBookName(editComment.getBook().getBookName());
            return "/comment/edit";
        }
        editComment.setComment(comment.getComment());
        repository.save(editComment);
        return "redirect:/book/comments/" + editComment.getBook().getId();
    }

    @GetMapping("/comment/create/{id}")
    public String create(@ModelAttribute("comment") CommentDTO comment) {
        Book book = bookRepository.findById(comment.getId()).orElseThrow(RuntimeException::new);
        comment.setBookId(book.getId());
        comment.setBookName(book.getBookName());
        return "/comment/create";
    }

    @Validated
    @PostMapping("/comment/create/{id}")
    public String create(@Valid @ModelAttribute("comment") CommentDTO comment,
                         BindingResult bindingResult) {
        Book book = bookRepository.findById(comment.getId()).orElseThrow(RuntimeException::new);
        if (bindingResult.hasErrors()) {
            comment.setBookId(book.getId());
            comment.setBookName(book.getBookName());
            return "/comment/create";
        }
        Comment newComment = new Comment(book, comment.getComment());
        repository.save(newComment);
        return "redirect:/book/comments/" + book.getId();
    }

    @DeleteMapping("/comment/delete/{id}")
    public @ResponseBody String edit(@PathVariable("id") long id) {
        Comment comment = repository.findById(id).orElseThrow(RuntimeException::new);
        repository.deleteById(id);
        return "/book/comments/" + comment.getBook().getId();
    }
}