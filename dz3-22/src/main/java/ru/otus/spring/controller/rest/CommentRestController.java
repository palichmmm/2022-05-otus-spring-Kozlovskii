package ru.otus.spring.controller.rest;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.models.Comment;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;

@RestController
public class CommentRestController {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    public CommentRestController(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/api/comment/book/{id}")
    public Flux<CommentDto> getAllCommentByBookId(@PathVariable("id") String id) {
        return commentRepository.findAllByBook_Id(id).map(CommentDto::toDto);
    }

    @PostMapping("/api/comment/book/{id}")
    public Mono<CommentDto> saveComment(@PathVariable("id") String id, @RequestBody CommentDto commentDto) {
        return bookRepository.findById(id).flatMap(book -> {
            Comment realComment = CommentDto.toComment(book, commentDto);
            return commentRepository.save(realComment);
        }).map(CommentDto::toDto);
    }

    @PutMapping("/api/comment/book/{id}")
    public Mono<CommentDto> updateComment(@PathVariable("id") String id, @RequestBody CommentDto commentDto) {
        return bookRepository.findById(id).flatMap(book -> {
            Comment realComment = CommentDto.toComment(book, commentDto);
            return commentRepository.save(realComment);
        }).map(CommentDto::toDto);
    }

    @DeleteMapping("/api/comment")
    public Mono<Void> deleteCommentById(@RequestParam("id") String id) {
        return commentRepository.deleteById(id);
    }

    @GetMapping("/api/comment/count")
    public Mono<Long> getCountComment() {
        return commentRepository.count();
    }

    @GetMapping("/api/comment/count/book/{id}")
    public Mono<Long> getCountCommentByBookId(@PathVariable("id") String id) {
        return commentRepository.countAllByBook_Id(id);
    }
}