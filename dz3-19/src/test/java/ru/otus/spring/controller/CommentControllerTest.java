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
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.rest.CommentController;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    public static final long BOOK_ID = 1;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper mapper;

    @DisplayName("REST API всех комментариев по книге должна вернуть статус 200 и JSON")
    @Test
    void REST_API_OfAllBookCommentsShouldReturnStatus200AndJSON() throws Exception {

        List<Comment> comments = List.of(
                new Comment(1, "Комментарий1", new Book(BOOK_ID)),
                new Comment(2, "Комментарий2", new Book(BOOK_ID))
        );
        List<CommentDto> commentDtos = comments.stream().map(CommentDto::toDto).collect(Collectors.toList());
        String expectedResult = mapper.writeValueAsString(commentDtos);
        Mockito.when(commentService.findAllCommentByBookId(BOOK_ID)).thenReturn(comments);
        mvc.perform(get("/api/comment/book/{id}", BOOK_ID))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResult))
                .andExpect(status().isOk());
    }
}
