package ru.otus.spring.rest;

import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.models.Book;
import ru.otus.spring.service.BookService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/api/book")
    public List<BookDto> getAllBook() {
        return service.findAll().stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @PostMapping("/api/book")
    public BookDto saveBook(@Valid @RequestBody BookDto book) {
        Book realBook = BookDto.toBook(book, new Book(0));
        return BookDto.toDto(service.save(realBook));
    }

    @PutMapping("/api/book")
    public BookDto updateBook(@Valid @RequestBody BookDto book) {
        Book realBook = BookDto.toBook(book, service.findById(book.getId()));
        return BookDto.toDto(service.save(realBook));
    }

    @DeleteMapping("/api/book")
    public void deleteBookById(@RequestParam("id") long id) {
        service.deleteById(id);
    }

    @GetMapping("/api/book/count")
    public long getCountBook() {
        return service.count();
    }
}
