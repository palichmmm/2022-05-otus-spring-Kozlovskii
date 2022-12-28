package ru.otus.spring.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.BookDtoMapper;
import ru.otus.spring.models.Book;
import ru.otus.spring.repository.BookRepository;

@RestController
public class BookRestController {

    private final BookRepository repository;

    private final BookDtoMapper bookDtoMapper;

    public BookRestController(BookRepository repository, BookDtoMapper bookDtoMapper) {
        this.repository = repository;
        this.bookDtoMapper = bookDtoMapper;
    }

    @GetMapping("/api/book")
    public Flux<BookDto> getAllBook() {
        return repository.findAll().map(bookDtoMapper::toDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/api/book", consumes = {"application/json"})
    public Mono<BookDto> saveBook(@RequestBody BookDto bookDto) {
        Book realBook = bookDtoMapper.toBook(bookDto, new Book(null));
        return repository.save(realBook).map(bookDtoMapper::toDto);
    }

    @PutMapping(value = "/api/book", consumes = {"application/json"})
    public Mono<BookDto> updateBook(@RequestBody BookDto bookDto) {
        return repository.findById(bookDto.getId()).flatMap(book -> {
            Book realBook = bookDtoMapper.toBook(bookDto, book);
            return repository.save(realBook);
        }).map(bookDtoMapper::toDto);
    }

    @DeleteMapping("/api/book")
    public Mono<Void> deleteBookById(@RequestParam("id") String id) {
        return repository.deleteById(id);
    }

    @GetMapping("/api/book/count")
    public Mono<Long> getCountBook() {
        return repository.count();
    }
}
