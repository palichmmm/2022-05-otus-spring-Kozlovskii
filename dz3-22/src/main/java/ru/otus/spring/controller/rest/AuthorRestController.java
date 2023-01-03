package ru.otus.spring.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.models.Author;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;

@RestController
public class AuthorRestController {

    private final AuthorRepository repository;
    private final BookRepository bookRepository;

    public AuthorRestController(AuthorRepository repository,
                                BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
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

    @DeleteMapping("/api/author/{id}")
    public Mono<ResponseEntity<Void>> deleteAuthorById(@PathVariable("id") String id) {
        return bookRepository.existsByAuthor_Id(id).flatMap(result -> {
            if (result) {
                return Mono.error(new RuntimeException("Удалить невозможно. Объект используется."));
            }
            return repository.deleteById(id).map(ResponseEntity::ok);
        });
    }

    @GetMapping("/api/author/count")
    public Mono<Long> getCountAuthor() {
        return repository.count();
    }
}
