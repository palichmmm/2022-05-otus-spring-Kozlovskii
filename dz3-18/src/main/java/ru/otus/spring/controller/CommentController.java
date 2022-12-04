package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String getCommentEditPage(@ModelAttribute("comment") CommentDTO comment) {
        Comment editComment = commentService.findById(comment.getId());
        comment.setComment(editComment.getComment());
        comment.setBookId(editComment.getBook().getId());
        comment.setBookName(editComment.getBook().getBookName());
        return "/comment/edit";
    }

    @PostMapping("/comment/edit/{id}")
    public String saveCommentEditPage(@Valid @ModelAttribute("comment") CommentDTO comment,
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
    public String getCommentByBookIdCreationPage(@ModelAttribute("comment") CommentDTO comment) {
        Book book = bookService.findById(comment.getId());
        comment.setBookId(book.getId());
        comment.setBookName(book.getBookName());
        return "/comment/create";
    }

    @PostMapping("/comment/create/{id}")
    public String saveCommentByBookIdCreationPage(@Valid @ModelAttribute("comment") CommentDTO comment,
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

    @GetMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable("id") long id, Model model) {
        model.addAttribute("comment", commentService.findById(id));
        return "/comment/delete";
    }

    @PostMapping("/comment/delete")
    public String delete(@RequestParam("id") long id, @RequestParam("bookId") long bookId) {
        commentService.deleteById(id);
        return "redirect:/book/comments/" + bookId;
    }
}