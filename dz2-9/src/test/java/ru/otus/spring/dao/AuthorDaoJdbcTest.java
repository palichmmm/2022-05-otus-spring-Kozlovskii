package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.models.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Dao для работы с авторами должно")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {
    private static final int EXPECTED_AUTHORS_COUNT = 3;
    @Autowired
    private AuthorDaoJdbc dao;

    @DisplayName("возвращать ожидаемое количество авторов в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        long actualAuthorsCount = dao.count();
        assertThat(actualAuthorsCount).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @DisplayName("добавлять авторов в БД")
    @Test
    void shouldInsertAuthor() {
        Author expectedAuthor = new Author(0, "Sir Arthur Ignatius Conan Doyle");
        dao.insert(expectedAuthor);
        Author actualAuthor = dao.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("возвращать ожидаемого автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author expectedAuthor = new Author(1, "George Eliot");
        Author actualAuthor = dao.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("удалять заданного автора по его id")
    @Test
    void shouldCorrectDeleteAuthorById() {
        Author author = new Author(0, "Sir Arthur Ignatius Conan Doyle");
        dao.insert(author);
        assertThatCode(() -> dao.getById(author.getId())).doesNotThrowAnyException();
        dao.deleteById(author.getId());
        assertThatCode(() -> dao.getById(author.getId())).isNull();
    }

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldReturnExpectedAuthorsList() {
        List<Author> expectedAuthor = List.of(
                new Author(1, "George Eliot"),
                new Author(2, "Virginia Woolf"),
                new Author(3, "Charles Dickens")
        );
        List<Author> actualAuthors = dao.getAll();
        assertThat(actualAuthors).containsExactlyInAnyOrderElementsOf(expectedAuthor);
    }
}