package ru.otus.spring.rest;

import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.models.Genre;
import ru.otus.spring.service.GenreService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GenreController {

    private final GenreService service;

    public GenreController(GenreService service) {
        this.service = service;
    }

    @GetMapping("/api/genre")
    public List<GenreDto> getAllGenre() {
        return service.findAll().stream().map(GenreDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/genre/{id}")
    public GenreDto getGenre(@PathVariable("id") long id) {
        return GenreDto.toDto(service.findById(id));
    }

    @PostMapping("/api/genre")
    public GenreDto saveGenre(@Valid @RequestBody GenreDto genre) {
        Genre realGenre = GenreDto.toGenre(genre);
        return GenreDto.toDto(service.save(realGenre));
    }

    @PutMapping("/api/genre")
    public GenreDto updateGenre(@Valid @RequestBody GenreDto genre) {
        Genre realGenre = GenreDto.toGenre(genre);
        return GenreDto.toDto(service.save(realGenre));
    }

    @DeleteMapping("/api/genre")
    public void deleteGenreById(@RequestParam("id") long id) {
        service.deleteById(id);
    }

    @GetMapping("/api/genre/count")
    public long getCountGenre() {
        return service.count();
    }
}
