package ru.otus.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dto.CommentDTO;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.models.Genre;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    public static final long COMMENT_ID = 1;
    public static final long BOOK_ID = 2;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private BookService bookService;

    @WithMockUser
    @DisplayName("Страница редактирования отзыва должна вернуть статус 200")
    @Test
    void commentEditFormPageShouldReturnStatus200() throws Exception {
        Comment comment = new Comment(COMMENT_ID, "Комментарий", new Book());
        CommentDTO commentDTO = new CommentDTO(comment.getId(), comment.getComment(), comment.getBook().getId(), comment.getBook().getBookName());
        Mockito.when(commentService.findById(COMMENT_ID)).thenReturn(comment);
        mvc.perform(get("/comment/edit/{id}", COMMENT_ID))
                .andDo(print())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("/comment/edit"))
                .andExpect(model().attribute("comment", commentDTO))
                .andExpect(status().isOk());
    }

    @WithMockUser
    @DisplayName("Страница создания отзыва должна вернуть статус 200")
    @Test
    void commentCreateFormPageShouldReturnStatus200() throws Exception {
        Book book = new Book(BOOK_ID, "Книга", new Author(), new Genre(), List.of(new Comment()));
        CommentDTO commentDTO = new CommentDTO(book.getId(), null, book.getId(), book.getBookName());
        Mockito.when(bookService.findById(BOOK_ID)).thenReturn(book);
        mvc.perform(get("/comment/create/{id}", BOOK_ID))
                .andDo(print())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("/comment/create"))
                .andExpect(model().attribute("comment", commentDTO))
                .andExpect(status().isOk());
    }
}
