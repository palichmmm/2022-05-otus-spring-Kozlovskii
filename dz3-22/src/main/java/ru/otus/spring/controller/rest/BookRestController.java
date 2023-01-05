package ru.otus.spring.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.BookDtoMapper;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.GenreRepository;

@RestController
public class BookRestController {

    private final BookRepository repository;

    private final BookDtoMapper bookDtoMapper;
    private final CommentRepository commentRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookRestController(BookRepository repository, BookDtoMapper bookDtoMapper,
                              CommentRepository commentRepository,
                              AuthorRepository authorRepository,
                              GenreRepository genreRepository) {
        this.repository = repository;
        this.bookDtoMapper = bookDtoMapper;
        this.commentRepository = commentRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping("/api/book")
    public Flux<BookDto> getAllBook() {
        return repository.findAll().map(bookDtoMapper::toDto);
    }

    @GetMapping("/api/book/{id}")
    public Mono<BookDto> getAllBook(@PathVariable("id") String id) {
        return repository.findById(id).map(bookDtoMapper::toDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/api/book")
    public Mono<BookDto> saveBook(@RequestBody BookDto bookDto) {
        Mono<Author> authorMono = authorRepository.findById(bookDto.getAuthorId());
        Mono<Genre> genreMono = genreRepository.findById(bookDto.getGenreId());
        return Mono.zip(authorMono, genreMono, (author, genre) ->
                        new Book(null, bookDto.getBookName(), author, genre)
                )
                .flatMap(repository::save)
                .map(bookDtoMapper::toDto);
    }

    @PutMapping(value = "/api/book")
    public Mono<BookDto> updateBook(@RequestBody BookDto bookDto) {
        Mono<Author> authorMono = authorRepository.findById(bookDto.getAuthorId());
        Mono<Genre> genreMono = genreRepository.findById(bookDto.getGenreId());
        return Mono.zip(authorMono, genreMono, (author, genre) ->
                        new Book(bookDto.getId(), bookDto.getBookName(), author, genre)
                )
                .flatMap(repository::save)
                .map(bookDtoMapper::toDto);
    }

    @DeleteMapping("/api/book/{id}")
    public Mono<Void> deleteBookById(@PathVariable("id") String id) {
        return Mono.when(repository.deleteById(id), commentRepository.deleteAllByBook_Id(id));
    }

    @GetMapping("/api/book/count")
    public Mono<Long> getCountBook() {
        return repository.count();
    }
}
