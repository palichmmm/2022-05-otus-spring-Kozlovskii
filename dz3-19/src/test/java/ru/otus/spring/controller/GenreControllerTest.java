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
import ru.otus.spring.models.Genre;
import ru.otus.spring.rest.GenreController;
import ru.otus.spring.service.GenreService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    public static final long GENRE_ID = 1;
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GenreService genreService;

    @DisplayName("REST API всех жанров должна вернуть статус 200 и JSON")
    @Test
    void GenresREST_API_ShouldReturnStatus200AndJSON() throws Exception {
        List<Genre> genres = List.of(new Genre(GENRE_ID, "Жанр1"), new Genre(2, "Жанр2"));
        String expectedResult = mapper.writeValueAsString(genres);
        Mockito.when(genreService.findAll()).thenReturn(genres);
        mvc.perform(get("/api/genre"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResult))
                .andExpect(status().isOk());
    }

    @DisplayName("REST API автора должна вернуть статус 200 и JSON")
    @Test
    void GenreREST_API_ShouldReturnStatus200AndJSON() throws Exception {
        Genre genre = new Genre(GENRE_ID, "Жанр");
        String expectedResult = mapper.writeValueAsString(genre);
        Mockito.when(genreService.findById(GENRE_ID)).thenReturn(genre);
        mvc.perform(get("/api/genre/{id}", GENRE_ID))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResult))
                .andExpect(status().isOk());
    }

    @DisplayName("REST API создания жанра должна вернуть статус 200 и JSON")
    @Test
    void GenreCreation_REST_API_ShouldReturnStatus200AndJSON() throws Exception {
        Genre genre = new Genre(0, "Жанр");
        String postBody = mapper.writeValueAsString(genre);
        Mockito.when(genreService.save(genre)).thenReturn(genre);
        mvc.perform(post("/api/genre").contentType(MediaType.APPLICATION_JSON).content(postBody))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(postBody))
                .andExpect(status().isOk());
    }
}
