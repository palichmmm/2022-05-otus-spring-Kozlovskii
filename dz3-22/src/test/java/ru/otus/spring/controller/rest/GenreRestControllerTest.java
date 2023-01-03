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
import ru.otus.spring.models.Genre;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@WebFluxTest(GenreRestController.class)
@ContextConfiguration(classes = {GenreRestController.class})
class GenreRestControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GenreRepository repository;

    @MockBean
    private BookRepository bookRepository;

    @Test
    @DisplayName("Должен выводить все жанры")
    public void ShouldOutputAllGenres() {
        Genre genre1 = new Genre("111", "Жанр1");
        Genre genre2 = new Genre("222", "Жанр2");
        Flux<Genre> genreFlux = Flux.just(genre1, genre2);
        when(repository.findAll()).thenReturn(genreFlux);
        webTestClient.get()
                .uri("/api/genre")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("Должен выводить жанр по Id")
    public void ShouldDisplayGenreById() {
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
    public void ShouldDeleteGenreById() {
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
    public void ShouldCreateGenre() {
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