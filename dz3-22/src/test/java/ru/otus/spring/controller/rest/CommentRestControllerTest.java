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
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;

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
    @DisplayName("Должен удалять комментарий по Id")
    public void shouldDeleteCommentById() {
        Mono<Void> voidReturn = Mono.empty();
        when(repository.deleteById("111")).thenReturn(voidReturn);

        webTestClient.delete()
                .uri("/api/comment/111")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Должен создавать комментарий по книге")
    public void shouldCreateCommentByBookId() {
        Book book = new Book("123", "Книга1", new Author("11", "Автор1"), new Genre("11", "Жанр1"));
        Comment comment = new Comment("111", "Комментарий1", book);
        Mono<Comment> commentMono = Mono.just(comment);

        when(bookRepository.findById("123")).thenReturn(Mono.just(book));
        when(repository.save(comment)).thenReturn(commentMono);

        webTestClient.post()
                .uri("/api/comment/book/123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(CommentDto.toDto(comment)), CommentDto.class)
                .exchange()
                .expectStatus().isCreated();
    }
}