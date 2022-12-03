package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.CommentDTO;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import javax.validation.Valid;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final BookService bookService;

    public CommentController(CommentService commentService, BookService bookService) {
        this.commentService = commentService;
        this.bookService = bookService;
    }

    @GetMapping("/comment/edit/{id}")
    public String editForm(@ModelAttribute("comment") CommentDTO comment) {
        Comment editComment = commentService.findById(comment.getId());
        comment.setComment(editComment.getComment());
        comment.setBookId(editComment.getBook().getId());
        comment.setBookName(editComment.getBook().getBookName());
        return "/comment/edit";
    }

    @PostMapping("/comment/edit/{id}")
    public String saveFormEdit(@Valid @ModelAttribute("comment") CommentDTO comment,
                       BindingResult bindingResult) {
        Comment editComment = commentService.findById(comment.getId());
        if (bindingResult.hasErrors()) {
            comment.setBookId(editComment.getBook().getId());
            comment.setBookName(editComment.getBook().getBookName());
            return "/comment/edit";
        }
        editComment.setComment(comment.getComment());
        commentService.save(editComment);
        return "redirect:/book/comments/" + editComment.getBook().getId();
    }

    @GetMapping("/comment/create/{id}")
    public String createForm(@ModelAttribute("comment") CommentDTO comment) {
        Book book = bookService.findById(comment.getId());
        comment.setBookId(book.getId());
        comment.setBookName(book.getBookName());
        return "/comment/create";
    }

    @PostMapping("/comment/create/{id}")
    public String saveFormCreate(@Valid @ModelAttribute("comment") CommentDTO comment,
                         BindingResult bindingResult) {
        Book book = bookService.findById(comment.getId());
        if (bindingResult.hasErrors()) {
            comment.setBookId(book.getId());
            comment.setBookName(book.getBookName());
            return "/comment/create";
        }
        Comment newComment = new Comment(book, comment.getComment());
        commentService.save(newComment);
        return "redirect:/book/comments/" + book.getId();
    }

    @DeleteMapping("/comment/delete/{id}")
    public @ResponseBody String delete(@PathVariable("id") long id) {
        Comment comment = commentService.findById(id);
        commentService.deleteById(id);
        return "/book/comments/" + comment.getBook().getId();
    }
}