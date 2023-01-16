package ru.otus.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dto.BookDTO;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.models.Genre;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    public static final int BOOK_ID = 1;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private MutableAclService mutableAclService;

    @WithMockUser
    @DisplayName("Страница всех книг должна вернуть статус 200")
    @Test
    void allBooksPageShouldReturnStatus200() throws Exception {
        List<Book> books = List.of(
                new Book(BOOK_ID, "Книга1", new Author(), new Genre(), List.of(new Comment())),
                new Book(2, "Книга2", new Author(), new Genre(), List.of(new Comment())));
        Mockito.when(bookService.findAll()).thenReturn(books);
        mvc.perform(get("/book/all"))
                .andDo(print())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("/book/all"))
                .andExpect(model().attribute("books", books))
                .andExpect(status().isOk());
    }

    @WithAnonymousUser
    @DisplayName("/book/all - Неавторизованным вернуть статус 401")
    @Test
    void allBooksPageUnauthorizedReturnStatus401() throws Exception {
        mvc.perform(get("/book/all"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser
    @DisplayName("Страница редактирования книги должна вернуть статус 200")
    @Test
    void bookEditPageShouldReturnStatus200() throws Exception {
        List<Author> authors = List.of(new Author("Автор"));
        List<Genre> genres = List.of(new Genre("Жанр"));
        Book book = new Book(BOOK_ID, "Книга", new Author(), new Genre(), null);
        BookDTO bookDTO = new BookDTO(book.getId(), book.getBookName(), book.getAuthor().getAuthorName(), book.getGenre().getGenreName());
        Mockito.when(bookService.findById(BOOK_ID)).thenReturn(book);
        Mockito.when(authorService.findAll()).thenReturn(authors);
        Mockito.when(genreService.findAll()).thenReturn(genres);
        mvc.perform(get("/book/edit/{id}", BOOK_ID))
                .andDo(print())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("/book/edit"))
                .andExpect(model().attribute("book", bookDTO))
                .andExpect(model().attribute("authors", authors))
                .andExpect(model().attribute("genres", genres))
                .andExpect(status().isOk());
    }

    @WithAnonymousUser
    @DisplayName("/book/edit/{id} - Неавторизованным вернуть статус 401")
    @Test
    void bookEditPageUnauthorizedReturnStatus401() throws Exception {
        mvc.perform(get("/book/edit/{id}", BOOK_ID))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser
    @DisplayName("Страница создания книги должна вернуть статус 200")
    @Test
    void bookCreateFormPageShouldReturnStatus200() throws Exception {
        BookDTO bookDTO = new BookDTO();
        List<Author> authors = List.of(new Author("Автор"));
        List<Genre> genres = List.of(new Genre("Жанр"));
        Mockito.when(authorService.findAll()).thenReturn(authors);
        Mockito.when(genreService.findAll()).thenReturn(genres);
        mvc.perform(get("/book/create"))
                .andDo(print())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("/book/create"))
                .andExpect(model().attribute("book", bookDTO))
                .andExpect(model().attribute("authors", authors))
                .andExpect(model().attribute("genres", genres))
                .andExpect(status().isOk());
    }

    @WithAnonymousUser
    @DisplayName("/book/create - Неавторизованным вернуть статус 401")
    @Test
    void bookCreateFormPageUnauthorizedReturnStatus401() throws Exception {
        mvc.perform(get("/book/create"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
