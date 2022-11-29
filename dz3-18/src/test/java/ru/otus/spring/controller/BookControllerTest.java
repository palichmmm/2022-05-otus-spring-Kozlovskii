package ru.otus.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @DisplayName("Страница всех книг должна вернуть статус 200")
    @Test
    void AllBooksPageShouldReturnStatus200() throws Exception {
        mvc.perform(get("/book/all")).andExpect(status().isOk());
    }

    @DisplayName("Страница редактирования книги должна вернуть статус 200")
    @Test
    void BookEditPageShouldReturnStatus200() throws Exception {
        mvc.perform(get("/book/edit/1")).andExpect(status().isOk());
    }

    @DisplayName("Страница создания книги должна вернуть статус 200")
    @Test
    void BookCreationPageShouldReturnStatus200() throws Exception {
        mvc.perform(get("/book/create")).andExpect(status().isOk());
    }
}
