package ru.otus.spring.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

@RestController
public class GenreRestController {

    private final GenreRepository repository;

    private final BookRepository bookRepository;

    public GenreRestController(GenreRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/api/genre")
    public Flux<GenreDto> getAllGenre() {
        return repository.findAll().map(GenreDto::toDto);
    }

    @GetMapping("/api/genre/{id}")
    public Mono<GenreDto> getGenre(@PathVariable("id") String id) {
        return repository.findById(id).map(GenreDto::toDto);
    }

    @PostMapping("/api/genre")
    public Mono<GenreDto> saveGenre(@RequestBody GenreDto genre) {
        Genre realGenre = GenreDto.toGenre(genre);
        return repository.save(realGenre).map(GenreDto::toDto);
    }

    @PutMapping("/api/genre")
    public Mono<GenreDto> updateGenre(@RequestBody GenreDto genre) {
        Genre realGenre = GenreDto.toGenre(genre);
        return repository.save(realGenre).map(GenreDto::toDto);
    }

    @DeleteMapping("/api/genre/{id}")
    public Mono<ResponseEntity<Void>> deleteGenreById(@PathVariable("id") String id) {
        return bookRepository.existsByGenre_Id(id).flatMap(result -> {
            if (result) {
                return Mono.error(new RuntimeException("Удалить невозможно. Объект используется."));
            }
            return repository.deleteById(id).map(ResponseEntity::ok);
        });
    }

    @GetMapping("/api/genre/count")
    public Mono<Long> getCountGenre() {
        return repository.count();
    }
}
