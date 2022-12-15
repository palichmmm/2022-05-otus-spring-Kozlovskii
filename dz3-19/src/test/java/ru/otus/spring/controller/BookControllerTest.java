package ru.otus.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.rest.BookController;
import ru.otus.spring.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    public static final int BOOK_ID = 1;
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @DisplayName("REST API всех книг должна вернуть статус 200 и JSON")
    @Test
    void BooksREST_API_ShouldReturnStatus200AndJSON() throws Exception {
        List<Book> books = List.of(
                new Book(1, "Книга1", new Author(1, "Автор1"), new Genre(1, "Жанр1")),
                new Book(2, "Книга2", new Author(2, "Автор2"), new Genre(2, "Жанр2"))
        );
        List<BookDto> bookDtos = books.stream().map(BookDto::toDto).collect(Collectors.toList());
        String expectedResult = mapper.writeValueAsString(bookDtos);
        Mockito.when(bookService.findAll()).thenReturn(books);
        mvc.perform(get("/api/book"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResult))
                .andExpect(status().isOk());
    }

    // Не могу разобраться с этим тестом. Ошибка 404. Почему он не может Json преобразовать в BookDto
    // Пробовал много советов из интернета, но ничего не помогло.
    // Помогите разобраться если это возможно.
    @DisplayName("REST API создания книги должна вернуть статус 200 и JSON")
    @Test
    void BookCreation_REST_API_ShouldReturnStatus200AndJSON() throws Exception {
        Author author = new Author(1, "Автор1");
        Genre genre = new Genre(1, "Жанр1");
        Book book = new Book(0, "Книга1", author, genre);

        String requestForm = "{authorId: 1, genreId: 1, bookName: Книга1}";
        String requestForm1 = "{\"authorId\": 1, \"genreId\": 1, \"bookName\": \"Книга1\"}";
        BookDto requestForm2 = new BookDto(1, 1, 1, "Книга1", "Автор1", "Жанр1");

        String requestBody = mapper.writeValueAsString(requestForm);
        String expectedResult = mapper.writeValueAsString(book);

        Mockito.when(bookService.save(book)).thenReturn(book);

        mvc.perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResult))
                .andExpect(status().isOk());
    }
}
