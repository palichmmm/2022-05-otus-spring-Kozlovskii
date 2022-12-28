package ru.otus.spring.controller.rest;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.models.Author;
import ru.otus.spring.repository.AuthorRepository;

@RestController
public class AuthorRestController {

    private final AuthorRepository repository;

    public AuthorRestController(AuthorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/author")
    public Flux<AuthorDto> getAllAuthor() {
        return repository.findAll().map(AuthorDto::toDto);
    }

    @GetMapping("/api/author/{id}")
    public Mono<AuthorDto> getAuthor(@PathVariable("id") String id) {
        return repository.findById(id).map(AuthorDto::toDto);
    }

    @PostMapping("/api/author")
    public Mono<AuthorDto> saveAuthor(@RequestBody AuthorDto authorDto) {
        Author realAuthor = AuthorDto.toAuthor(authorDto);
        return repository.save(realAuthor).map(AuthorDto::toDto);
    }

    @PutMapping("/api/author")
    public Mono<AuthorDto> updateAuthor(@RequestBody AuthorDto authorDto) {
        Author realAuthor = AuthorDto.toAuthor(authorDto);
        return repository.save(realAuthor).map(AuthorDto::toDto);
    }

    @DeleteMapping("/api/author")
    public Mono<Void> deleteAuthorById(@RequestParam("id") long id) {
        return repository.deleteById(id);
    }

    @GetMapping("/api/author/count")
    public Mono<Long> getCountAuthor() {
        return repository.count();
    }
}
