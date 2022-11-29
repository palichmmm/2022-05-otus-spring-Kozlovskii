package ru.otus.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private BookService bookService;

    @DisplayName("Страница редактирования отзыва должна вернуть статус 200")
    @Test
    void CommentEditPageShouldReturnStatus200() throws Exception {
        mvc.perform(get("/comment/edit/1")).andExpect(status().isOk());
    }

    @DisplayName("Страница создания отзыва должна вернуть статус 200")
    @Test
    void CommentCreationPageShouldReturnStatus200() throws Exception {
        mvc.perform(get("/comment/create/1")).andExpect(status().isOk());
    }
}
