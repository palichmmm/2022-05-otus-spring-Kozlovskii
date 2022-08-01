package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Dao для работы с жанрами должно")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {
    private static final int EXPECTED_GENRES_COUNT = 3;
    @Autowired
    private GenreDaoJdbc dao;

    @DisplayName("возвращать ожидаемое количество жанров в БД")
    @Test
    void shouldReturnExpectedGenreCount() {
        long actualGenresCount = dao.count();
        assertThat(actualGenresCount).isEqualTo(EXPECTED_GENRES_COUNT);
    }

    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {
        Genre expectedGenre = new Genre(0, "History");
        dao.insert(expectedGenre);
        Genre actualGenre = dao.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("возвращать ожидаемый жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre expectedGenre = new Genre(1, "Detective");
        Genre actualGenre = dao.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("удалять заданный жанр по его id")
    @Test
    void shouldCorrectDeleteGenreById() {
        Genre genre = new Genre(0, "History");
        dao.insert(genre);
        assertThatCode(() -> dao.getById(genre.getId())).doesNotThrowAnyException();
        dao.deleteById(genre.getId());
        assertThatCode(() -> dao.getById(genre.getId())).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать ожидаемый список жанров")
    @Test
    void shouldReturnExpectedGenreList() {
        List<Genre> expectedAuthor = List.of(
                new Genre(1, "Detective"),
                new Genre(2, "Novel"),
                new Genre(3, "Adventures")
        );
        List<Genre> actualGenres = dao.getAll();
        assertThat(actualGenres).containsExactlyInAnyOrderElementsOf(expectedAuthor);
    }
}