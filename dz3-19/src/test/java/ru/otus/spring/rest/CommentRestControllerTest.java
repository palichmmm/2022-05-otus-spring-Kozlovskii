package ru.otus.spring.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentRestController.class)
class CommentRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper mapper;

    @DisplayName("REST API всех комментариев по книге должна вернуть статус 200 и JSON")
    @Test
    void rest_API_OfAllBookCommentsShouldReturnStatus200AndJSON() throws Exception {

        List<Comment> comments = List.of(
                new Comment(1, "Комментарий1", new Book(1)),
                new Comment(2, "Комментарий2", new Book(1))
        );
        List<CommentDto> commentDtos = comments.stream().map(CommentDto::toDto).collect(Collectors.toList());
        String expectedResult = mapper.writeValueAsString(commentDtos);
        when(commentService.findAllCommentByBookId(1)).thenReturn(comments);
        mvc.perform(get("/api/comment/book/{id}", 1))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResult))
                .andExpect(status().isOk());
    }
}