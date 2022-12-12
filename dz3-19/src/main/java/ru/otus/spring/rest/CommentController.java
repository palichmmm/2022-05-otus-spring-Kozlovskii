package ru.otus.spring.rest;

import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentController {

    private final CommentService service;

    private final BookService bookService;

    public CommentController(CommentService service, BookService bookService) {
        this.service = service;
        this.bookService = bookService;
    }

    @GetMapping("/api/comment/book/{id}")
    public List<CommentDto> getAllCommentByBookId(@PathVariable("id") long id) {
        return service.findAllCommentByBookId(id).stream().map(CommentDto::toDto).collect(Collectors.toList());
    }
//
//    @GetMapping("/api/comment/{id}")
//    public CommentDto getComment(@PathVariable("id") long id) {
//        return CommentDto.toDto(service.findById(id));
//    }

    @PostMapping("/api/comment/book/{id}")
    public CommentDto saveComment(@PathVariable("id") long id, @Valid @RequestBody CommentDto comment) {
        Book book = bookService.findById(id);
        Comment realComment = CommentDto.toComment(book, comment);
        return CommentDto.toDto(service.save(realComment));
    }

    @PutMapping("/api/comment/book/{id}")
    public CommentDto updateComment(@PathVariable("id") long id, @Valid @RequestBody CommentDto comment) {
        Book book = bookService.findById(id);
        Comment realComment = CommentDto.toComment(book, comment);
        return CommentDto.toDto(service.save(realComment));
    }

    @DeleteMapping("/api/comment")
    public void deleteCommentById(@RequestParam("id") long id) {
        service.deleteById(id);
    }

    @GetMapping("/api/comment/count")
    public long getCountComment() {
        return service.count();
    }

    @GetMapping("/api/comment/count/book/{id}")
    public long getCountCommentByBookId(@PathVariable("id") long id) {
        return service.countAllByBookId(id);
    }
}