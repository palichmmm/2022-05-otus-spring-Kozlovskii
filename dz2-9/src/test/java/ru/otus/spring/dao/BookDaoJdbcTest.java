package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.models.Book;

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

    @DisplayName("возвращать ожидаемое количество книг в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        long actualBooksCount = dao.count();
        assertThat(actualBooksCount).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Book expectedBook = new Book(0, "Колобок", null, null);
        dao.insert(expectedBook);
        Book actualBook = dao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возвращать ожидаемую книгу по его id")
    @Test
    void shouldReturnExpectedBookById() {
        Book expectedBook = new Book(0, "Adam Beed",null, null);
        Book actualBook = dao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("удалять заданного автора по его id")
    @Test
    void shouldCorrectDeleteBookById() {
        Book Book = new Book(0, "Adam Beed",null, null);
        dao.insert(Book);
        assertThatCode(() -> dao.getById(Book.getId())).doesNotThrowAnyException();
        dao.deleteById(Book.getId());
        assertThatCode(() -> dao.getById(Book.getId())).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldReturnExpectedBooksList() {
        List<Book> expectedBook = List.of(
                new Book(0, "Adam Beed",null, null),
                new Book(0, "Adam Beed",null, null),
                new Book(0, "Adam Beed",null, null)
        );
        List<Book> actualBooks = dao.getAll();
        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(expectedBook);
    }
}
