package ru.otus.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.service.AuthorService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @DisplayName("Страница всех авторов должна вернуть статус 200")
    @Test
    void AllAuthorsPageShouldReturnStatus200() throws Exception {
        mvc.perform(get("/author/all")).andExpect(status().isOk());
    }

    @DisplayName("Страница редактирования автора должна вернуть статус 200")
    @Test
    void AuthorEditPageShouldReturnStatus200() throws Exception {
        mvc.perform(get("/author/edit/(1)")).andExpect(status().isOk());
    }

    @DisplayName("Страница создания автора должна вернуть статус 200")
    @Test
    void AuthorCreationPageShouldReturnStatus200() throws Exception {
        mvc.perform(get("/author/create")).andExpect(status().isOk());
    }
}