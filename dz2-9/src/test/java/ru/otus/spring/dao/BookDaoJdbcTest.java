package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
public class BookDaoJdbcTest {
    private static final int EXPECTED_BOOK_COUNT = 6;
    @Autowired
    private BookDaoJdbc dao;
    private static final Author AUTHOR = new Author(1, "George Eliot");
    private static final Genre GENRE = new Genre(2, "Novel");

    @DisplayName("возвращать ожидаемое количество книг в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        long actualBooksCount = dao.count();
        assertThat(actualBooksCount).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Book expectedBook = new Book(0, "Колобок", AUTHOR, GENRE);
        dao.insert(expectedBook);
        Book actualBook = dao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возвращать ожидаемую книгу по его id")
    @Test
    void shouldReturnExpectedBookById() {
        Book expectedBook = new Book(1, "Adam Beed", AUTHOR, GENRE);
        Book actualBook = dao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("удалять заданного автора по его id")
    @Test
    void shouldCorrectDeleteBookById() {
        Book book = new Book(0, "Adam Beed", AUTHOR, GENRE);
        dao.insert(book);
        assertThatCode(() -> dao.getById(book.getId())).doesNotThrowAnyException();
        dao.deleteById(book.getId());
        assertThatCode(() -> dao.getById(book.getId())).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldReturnExpectedBooksList() {
        List<Book> expectedBook = List.of(
                new Book(1, "Adam Beed", new Author(1, "George Eliot"), new Genre(2, "Novel")),
                new Book(2, "Mill on the Floss", new Author(1, "George Eliot"), new Genre(2, "Novel")),
                new Book(3, "By sea away", new Author(2, "Virginia Woolf"), new Genre(2, "Novel")),
                new Book(4, "Day and night", new Author(2, "Virginia Woolf"), new Genre(2, "Novel")),
                new Book(5, "Posthumous Papers of the Pickwick Club", new Author(3, "Charles Dickens"), new Genre(2, "Novel")),
                new Book(6, "The Adventures of Oliver Twist", new Author(3, "Charles Dickens"), new Genre(2, "Novel"))
        );
        List<Book> actualBooks = dao.getAll();
        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(expectedBook);
    }
}
