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
import ru.otus.spring.models.Author;
import ru.otus.spring.service.AuthorService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    public static final long AUTHOR_ID = 1;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @WithMockUser
    @DisplayName("Страница всех авторов должна вернуть статус 200")
    @Test
    void allAuthorsPageShouldReturnStatus200() throws Exception {
        List<Author> authors = List.of(new Author(1, "Автор1"), new Author(2, "Автор2"));
        Mockito.when(authorService.findAll()).thenReturn(authors);
        mvc.perform(get("/author/all"))
                .andDo(print())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("/author/all"))
                .andExpect(model().attribute("authors", authors))
                .andExpect(status().isOk());
    }

    @WithMockUser
    @DisplayName("Страница редактирования автора должна вернуть статус 200")
    @Test
    void authorEditFormPageShouldReturnStatus200() throws Exception {
        Author author = new Author(AUTHOR_ID, "Автор");
        Mockito.when(authorService.findById(AUTHOR_ID)).thenReturn(author);
        mvc.perform(get("/author/edit/{id}", AUTHOR_ID))
                .andDo(print())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("/author/edit"))
                .andExpect(model().attribute("author", author))
                .andExpect(status().isOk());
    }

    @WithMockUser
    @DisplayName("Страница создания автора должна вернуть статус 200")
    @Test
    void authorCreationFormPageShouldReturnStatus200() throws Exception {
        Author author = new Author();
        mvc.perform(get("/author/create"))
                .andDo(print())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("/author/create"))
                .andExpect(model().attribute("author", author))
                .andExpect(status().isOk());
    }

    @DisplayName("GET запросы. Неавторизованным вернуть статус 401")
    @WithAnonymousUser
    @ParameterizedTest(name = "{index} - GET {0} запрос статус {1}")
    @CsvSource({
            "/author/all, 401",
            "/author/edit/1, 401",
            "/author/create, 401",
            "/author/delete/1, 401",
    })
    void unauthorizedReturnStatus401(String url, int statusCode) throws Exception {
        mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is(statusCode));
    }
}