package ru.otus.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.service.GenreService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @DisplayName("Страница всех жанров должна вернуть статус 200")
    @Test
    void AllGenresPageShouldReturnStatus200() throws Exception {
        mvc.perform(get("/genre/all")).andExpect(status().isOk());
    }

    @DisplayName("Страница редактирования жанра должна вернуть статус 200")
    @Test
    void GenreEditPageShouldReturnStatus200() throws Exception {
        mvc.perform(get("/genre/edit/(1)")).andExpect(status().isOk());
    }

    @DisplayName("Страница создания жанра должна вернуть статус 200")
    @Test
    void GenreCreationPageShouldReturnStatus200() throws Exception {
        mvc.perform(get("/genre/create")).andExpect(status().isOk());
    }
}
