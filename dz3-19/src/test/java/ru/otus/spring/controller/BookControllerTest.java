package ru.otus.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.BookDtoMapper;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.rest.BookController;
import ru.otus.spring.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookDtoMapper dtoMapper;

    @MockBean
    private BookService bookService;

    @DisplayName("REST API всех книг должна вернуть статус 200 и JSON")
    @Test
    void booksREST_API_ShouldReturnStatus200AndJSON() throws Exception {
        Book book1 = new Book(1, "Книга1", new Author(1, "Автор1"), new Genre(1, "Жанр1"));
        Book book2 = new Book(2, "Книга2", new Author(2, "Автор2"), new Genre(2, "Жанр2"));
        BookDto bookDto1 = new BookDtoMapper().toDto(book1);
        BookDto bookDto2 = new BookDtoMapper().toDto(book2);
        List<Book> books = List.of(book1, book2);

        when(bookService.findAll()).thenReturn(books);
        when(dtoMapper.toDto(book1)).thenReturn(bookDto1);
        when(dtoMapper.toDto(book2)).thenReturn(bookDto2);

        List<BookDto> booksDto = books.stream().map(dtoMapper::toDto).collect(Collectors.toList());
        String expectedResponse = mapper.writeValueAsString(booksDto);

        mvc.perform(get("/api/book"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResponse))
                .andExpect(status().isOk());
    }

    @DisplayName("REST API создания книги должна вернуть статус 200 и JSON")
    @Test
    void bookCreation_REST_API_ShouldReturnStatus200AndJSON() throws Exception {
        Book saveBook = new Book(0, "Книга1", new Author(1, "Автор1"), new Genre(1, "Жанр1"));
        Book responseBook = new Book(1, "Книга1", new Author(1, "Автор1"), new Genre(1, "Жанр1"));
        BookDto saveBookDto = new BookDtoMapper().toDto(saveBook);
        BookDto responseDto = new BookDtoMapper().toDto(responseBook);

        when(dtoMapper.toDto(responseBook)).thenReturn(responseDto);
        when(dtoMapper.toBook(saveBookDto, new Book(0))).thenReturn(responseBook);
        when(bookService.save(saveBook)).thenReturn(saveBook);

        String requestBody = mapper.writeValueAsString(saveBookDto);
        String responseBody = mapper.writeValueAsString(responseDto);

        mvc.perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }
}
