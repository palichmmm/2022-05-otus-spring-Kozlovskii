package ru.otus.spring.controller.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.GenreRepository;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebFluxTest(CommentRestController.class)
@ContextConfiguration(classes = {CommentRestController.class})
class CommentRestControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CommentRepository repository;

    @MockBean
    private BookRepository bookRepository;

    @Test
    @DisplayName("Должен выводить все комментарии по книге")
    public void shouldOutputAllCommentsByBookId() {
        Book book = new Book("123", "Книга1", new Author("11", "Автор1"), new Genre("11", "Жанр1"));
        Comment comment1 = new Comment("111", "Комментарий1", book);
        Comment comment2 = new Comment("222", "Комментарий2", book);
        Flux<Comment> commentFlux = Flux.just(comment1, comment2);
        when(repository.findAllByBook_Id("123")).thenReturn(commentFlux);
        webTestClient.get()
                .uri("/api/comment/book/123")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("Должен выводить жанр по Id")
    public void shouldDisplayGenreById() {
        Genre genre = new Genre("111", "Жанр1");
        Mono<Genre> genreMono = Mono.just(genre);

        when(repository.findById("111")).thenReturn(genreMono);

        webTestClient.get()
                .uri("/api/genre/111")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GenreDto.class)
                .value(GenreDto::getId, equalTo("111"))
                .value(GenreDto::getGenreName, equalTo("Жанр1"));
    }

    @Test
    @DisplayName("Должен удалять жанр по Id")
    public void shouldDeleteGenreById() {
        Mono<Void> voidReturn = Mono.empty();
        when(bookRepository.existsByGenre_Id("111")).thenReturn(Mono.just(false));
        when(repository.deleteById("111")).thenReturn(voidReturn);

        webTestClient.delete()
                .uri("/api/genre/111")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Должен создавать жанр")
    public void shouldCreateGenre() {
        Genre genre = new Genre("111", "Жанр1");
        Mono<Genre> genreMono = Mono.just(genre);

        when(repository.save(genre)).thenReturn(genreMono);

        webTestClient.post()
                .uri("/api/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(GenreDto.toDto(genre)), GenreDto.class)
                .exchange()
                .expectStatus().isCreated();
    }
}