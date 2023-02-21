package ru.otus.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.models.Genre;
import ru.otus.spring.service.GenreService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    public static final long GENRE_ID = 1;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @WithMockUser
    @DisplayName("Страница всех жанров должна вернуть статус 200")
    @Test
    void allGenresPageShouldReturnStatus200() throws Exception {
        List<Genre> genres = List.of(new Genre(GENRE_ID, "Жанр1"), new Genre(2, "Жанр2"));
        Mockito.when(genreService.findAll()).thenReturn(genres);
        mvc.perform(get("/genre/all"))
                .andDo(print())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("/genre/all"))
                .andExpect(model().attribute("genres", genres))
                .andExpect(status().isOk());
    }

    @WithMockUser
    @DisplayName("Страница редактирования жанра должна вернуть статус 200")
    @Test
    void genreEditFormPageShouldReturnStatus200() throws Exception {
        Genre genre = new Genre(GENRE_ID, "Жанр");
        Mockito.when(genreService.findById(GENRE_ID)).thenReturn(genre);
        mvc.perform(get("/genre/edit/{id}", GENRE_ID))
                .andDo(print())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("/genre/edit"))
                .andExpect(model().attribute("genre", genre))
                .andExpect(status().isOk());
    }

    @WithMockUser
    @DisplayName("Страница создания жанра должна вернуть статус 200")
    @Test
    void genreCreateFormPageShouldReturnStatus200() throws Exception {
        Genre genre = new Genre();
        mvc.perform(get("/genre/create"))
                .andDo(print())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("/genre/create"))
                .andExpect(model().attribute("genre", genre))
                .andExpect(status().isOk());
    }

    @DisplayName("GET запросы. Неавторизованным вернуть статус 401")
    @WithAnonymousUser
    @ParameterizedTest(name = "{index} - GET {0} запрос статус {1}")
    @CsvSource({
            "/genre/all, 401",
            "/genre/edit/1, 401",
            "/genre/create, 401",
            "/genre/delete/1, 401",
    })
    void unauthorizedReturnStatus401(String url, int statusCode) throws Exception {
        mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is(statusCode));
    }
}
