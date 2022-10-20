package ru.otus.spring.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами ")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
public class GenreRepositoryJpaTest {

    public static final String GENRE_NAME = "Crime";
    public static final long GENRE_ID = 1L;
    public static final int RELATED_GENRE_ID = 2;
    public static final long NON_EXISTENT_ID = 10L;
    public static final int EXPECTED_NUMBER_OF_GENRES = 3;
    public static final String FIRST_GENRE_NAME = "Detective";
    @Autowired
    private GenreRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать информацию о нужном жанре по его id")
    @Test
    void findExpectedGenreById() {
        Optional<Genre> actualGenre = repositoryJpa.findById(GENRE_ID);
        Genre expectedGenre = em.find(Genre.class, GENRE_ID);
        assertThat(actualGenre).isPresent().get().usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("должен корректно сохранять всю информацию о жанре")
    @Test
    void saveAllGenreInfo() {
        Genre Genre = new Genre(0, GENRE_NAME);
        repositoryJpa.save(Genre);
        assertThat(Genre.getId()).isGreaterThan(0);

        Genre actualGenre = em.find(Genre.class, Genre.getId());
        assertThat(actualGenre).isNotNull().matches(g -> !g.getGenreName().equals(""));
    }

    @DisplayName("должен загружать список всех жанров с полной информацией о них")
    @Test
    void returnCorrectGenresListWithAllInfo() {
        List<Genre> Genre = repositoryJpa.findAll();
        assertThat(Genre).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES)
                .allMatch(g -> !g.getGenreName().equals(""));
    }

    @DisplayName("должен загружать информацию о жанре по его имени")
    @Test
    void findExpectedGenreByName() {
        Genre firstGenre = em.find(Genre.class, GENRE_ID);
        Optional<Genre> genres = repositoryJpa.findByName(FIRST_GENRE_NAME);

        assertThat(genres).isPresent().get().isEqualTo(firstGenre);
    }

    @DisplayName("должен изменять название жанра по его id")
    @Test
    void updateGenreNameById() {
        Genre firstGenre = em.find(Genre.class, GENRE_ID);
        String oldName = firstGenre.getGenreName();
        em.detach(firstGenre);

        firstGenre.setGenreName(GENRE_NAME);
        repositoryJpa.save(firstGenre);
        Genre updatedGenre = em.find(Genre.class, GENRE_ID);

        assertThat(updatedGenre.getGenreName()).isNotEqualTo(oldName).isEqualTo(GENRE_NAME);
    }

    @Transactional
    @DisplayName("должен удалять несвязанный жанр по его id")
    @Test
    void deleteGenreById() {
        Genre genre = new Genre(GENRE_NAME);
        repositoryJpa.save(genre);
        assertThat(genre.getId()).isGreaterThan(0);
        repositoryJpa.deleteById(genre.getId());

        Genre deletedGenre = em.find(Genre.class, genre.getId());
        assertThat(deletedGenre).isNull();
    }
}