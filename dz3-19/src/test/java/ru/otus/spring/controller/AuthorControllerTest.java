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
import ru.otus.spring.models.Author;
import ru.otus.spring.rest.AuthorController;
import ru.otus.spring.service.AuthorService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    public static final long AUTHOR_ID = 1;
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorService authorService;

    @DisplayName("REST API всех авторов должна вернуть статус 200 и JSON")
    @Test
    void AuthorsREST_API_ShouldReturnStatus200AndJSON() throws Exception {
        List<Author> authors = List.of(new Author(1, "Автор1"), new Author(2, "Автор2"));
        String expectedResult = mapper.writeValueAsString(authors);
        Mockito.when(authorService.findAll()).thenReturn(authors);
        mvc.perform(get("/api/author"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResult))
                .andExpect(status().isOk());
    }

    @DisplayName("REST API автора должна вернуть статус 200 и JSON")
    @Test
    void AuthorREST_API_ShouldReturnStatus200AndJSON() throws Exception {
        Author author = new Author(AUTHOR_ID, "Автор");
        String expectedResult = mapper.writeValueAsString(author);
        Mockito.when(authorService.findById(AUTHOR_ID)).thenReturn(author);
        mvc.perform(get("/api/author/{id}", AUTHOR_ID))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResult))
                .andExpect(status().isOk());
    }

    @DisplayName("REST API создания автора должна вернуть статус 200 и JSON")
    @Test
    void AuthorCreation_REST_API_ShouldReturnStatus200AndJSON() throws Exception {
        Author author = new Author(0, "Автор");
        String postBody = mapper.writeValueAsString(author);
        Mockito.when(authorService.save(author)).thenReturn(author);
        mvc.perform(post("/api/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postBody))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(postBody))
                .andExpect(status().isOk());
    }
}