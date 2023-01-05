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
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.BookDtoMapper;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.GenreRepository;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@WebFluxTest({BookRestController.class, CommentRestController.class, BookDtoMapper.class})
@ContextConfiguration(classes = {BookRestController.class, CommentRestController.class, BookDtoMapper.class})
class BookRestControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BookDtoMapper dtoMapper;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @Test
    @DisplayName("Должен выводить все книги")
    public void shouldOutputAllBooks() {
        Book book1 = new Book("111", "Книга1", new Author("11", "Автор1"), new Genre("1", "Жанр1"));
        Book book2 = new Book("222", "Книга2", new Author("22", "Автор2"), new Genre("2", "Жанр2"));
        Flux<Book> bookFlux = Flux.just(book1, book2);
        when(bookRepository.findAll()).thenReturn(bookFlux);
        webTestClient.get()
                .uri("/api/book")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("Должен выводить книгу по Id")
    public void shouldDisplayBookById() {
        Book book = new Book("111", "Книга1", new Author("11", "Автор1"), new Genre("1", "Жанр1"));
        Mono<Book> bookMono = Mono.just(book);

        when(bookRepository.findById("111")).thenReturn(bookMono);

        webTestClient.get()
                .uri("/api/book/111")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .value(BookDto::getId, equalTo("111"))
                .value(BookDto::getBookName, equalTo("Книга1"))
                .value(BookDto::getAuthorId, equalTo("11"))
                .value(BookDto::getAuthor, equalTo("Автор1"))
                .value(BookDto::getGenreId, equalTo("1"))
                .value(BookDto::getGenre, equalTo("Жанр1"));
    }

    @Test
    @DisplayName("Должен удалять книгу по Id")
    public void shouldDeleteBookById() {
        Mono<Void> voidReturn = Mono.empty();
        when(bookRepository.deleteById("111")).thenReturn(voidReturn);
        when(commentRepository.deleteAllByBook_Id("111")).thenReturn(voidReturn);

        webTestClient.delete()
                .uri("/api/book/111")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Должен создавать книгу")
    public void shouldCreateBook() {
        Genre genre = new Genre("1", "Жанр1");
        Author author = new Author("11", "Автор1");
        Book book = new Book(null, "Книга1", author, genre);
        Mono<Book> bookMono = Mono.just(book);
        Mono<Author> authorMono = Mono.just(author);
        Mono<Genre> genreMono = Mono.just(genre);

        when(authorRepository.findById("11")).thenReturn(authorMono);
        when(genreRepository.findById("1")).thenReturn(genreMono);
        when(bookRepository.save(book)).thenReturn(bookMono);

        webTestClient.post()
                .uri("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(dtoMapper.toDto(book)), BookDto.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }
}