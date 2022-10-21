package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.GenreRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами ")
@DataJpaTest
public class GenreRepositoryJpaTest {
    public static final long GENRE_ID = 1L;
    public static final String FIRST_GENRE_NAME = "Detective";
    @Autowired
    private GenreRepository repositoryJpa;
    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать информацию о жанре по его имени")
    @Test
    void findExpectedGenreByName() {
        Genre firstGenre = em.find(Genre.class, GENRE_ID);
        Optional<Genre> genres = repositoryJpa.findByGenreName(FIRST_GENRE_NAME);

        assertThat(genres).isPresent().get().isEqualTo(firstGenre);
    }
}