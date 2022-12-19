package ru.otus.spring.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.BookDtoMapper;
import ru.otus.spring.models.Book;
import ru.otus.spring.service.BookService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final BookService service;

    private final BookDtoMapper bookDtoMapper;

    public BookController(BookService service, BookDtoMapper bookDtoMapper) {
        this.service = service;
        this.bookDtoMapper = bookDtoMapper;
    }

    @GetMapping("/api/book")
    public List<BookDto> getAllBook() {
        return service.findAll().stream().map(bookDtoMapper::toDto).collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/api/book", consumes = {"application/json"})
    public BookDto saveBook(@Valid @RequestBody BookDto bookDto) {
        Book realBook = bookDtoMapper.toBook(bookDto, new Book(0));
        return bookDtoMapper.toDto(service.save(realBook));
    }

    @PutMapping(value = "/api/book", consumes = {"application/json"})
    public BookDto updateBook(@Valid @RequestBody BookDto bookDto) {
        Book realBook = bookDtoMapper.toBook(bookDto, service.findById(bookDto.getId()));
        return bookDtoMapper.toDto(service.save(realBook));
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
