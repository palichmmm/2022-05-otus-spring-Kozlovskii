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
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.models.Author;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@WebFluxTest(AuthorRestController.class)
@ContextConfiguration(classes = {AuthorRestController.class})
class AuthorRestControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AuthorRepository repository;

    @MockBean
    private BookRepository bookRepository;

    @Test
    @DisplayName("Должен выводить всех авторов")
    public void ShouldOutputAllAuthors() {
        Author author1 = new Author("111", "Автор1");
        Author author2 = new Author("222", "Автор2");
        Flux<Author> authorFlux = Flux.just(author1, author2);
        when(repository.findAll()).thenReturn(authorFlux);
        webTestClient.get()
                .uri("/api/author")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("Должен выводить автора по Id")
    public void ShouldDisplayAuthorById() {
        Author author = new Author("111", "Автор1");
        Mono<Author> authorMono = Mono.just(author);

        when(repository.findById("111")).thenReturn(authorMono);

        webTestClient.get()
                .uri("/api/author/111")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthorDto.class)
                .value(AuthorDto::getId, equalTo("111"))
                .value(AuthorDto::getAuthorName, equalTo("Автор1"));
    }

    @Test
    @DisplayName("Должен удалять автора по Id")
    public void ShouldDeleteAuthorById() {
        Mono<Void> voidReturn = Mono.empty();
        when(bookRepository.existsByAuthor_Id("111")).thenReturn(Mono.just(false));
        when(repository.deleteById("111")).thenReturn(voidReturn);

        webTestClient.delete()
                .uri("/api/author/111")
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    @DisplayName("Должен создавать автора")
    public void ShouldCreateAuthor() {
        Author author = new Author("111", "Автор1");
        Mono<Author> authorMono = Mono.just(author);

        when(repository.save(author)).thenReturn(authorMono);

        webTestClient.post()
                .uri("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(AuthorDto.toDto(author)), AuthorDto.class)
                .exchange()
                .expectStatus().isCreated();
    }
}