package ru.otus.spring.rest;

import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.models.Author;
import ru.otus.spring.service.AuthorService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @GetMapping("/api/author")
    public List<AuthorDto> getAllAuthor() {
        return service.findAll().stream().map(AuthorDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/author/{id}")
    public AuthorDto getAuthor(@PathVariable("id") long id) {
        return AuthorDto.toDto(service.findById(id));
    }

    @PostMapping("/api/author")
    public AuthorDto saveAuthor(@Valid @RequestBody AuthorDto author) {
        Author realAuthor = AuthorDto.toAuthor(author);
        return AuthorDto.toDto(service.save(realAuthor));
    }

    @PutMapping("/api/author")
    public AuthorDto updateAuthor(@Valid @RequestBody AuthorDto author) {
        Author realAuthor = AuthorDto.toAuthor(author);
        return AuthorDto.toDto(service.save(realAuthor));
    }

    @DeleteMapping("/api/author")
    public void deleteAuthor(@RequestParam("id") long id) {
        service.deleteById(id);
    }

    @GetMapping("/api/author/count")
    public long getCount() {
        return service.count();
    }
}
