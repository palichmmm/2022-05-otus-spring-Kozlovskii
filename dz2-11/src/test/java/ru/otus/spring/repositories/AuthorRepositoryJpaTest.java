package ru.otus.spring.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Репозиторий на основе Jpa для работы с авторами ")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
public class AuthorRepositoryJpaTest {
    public static final String AUTHOR_NAME = "Agatha Christie";
    public static final long AUTHOR_ID = 1L;
    public static final long NON_EXISTENT_ID = 10L;
    public static final int EXPECTED_NUMBER_OF_AUTHORS = 3;
    public static final String FIRST_AUTHOR_NAME = "George Eliot";
    @Autowired
    private AuthorRepositoryJpa repositoryJpa;
    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужном авторе по его id")
    @Transactional
    @Test
    void findExpectedAuthorById() {
        Optional<Author> actualAuthor = repositoryJpa.findById(AUTHOR_ID);
        Author expectedAuthor = em.find(Author.class, AUTHOR_ID);
        assertThat(actualAuthor).isPresent().get().usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("должен корректно сохранять всю информацию об авторах")
    @Transactional
    @Test
    void saveAllAuthorInfo() {
        Author author = new Author(AUTHOR_NAME);
        repositoryJpa.save(author);
        assertThat(author.getId()).isGreaterThan(0);

        Author actualAuthor = em.find(Author.class, author.getId());
        assertThat(actualAuthor).isNotNull().matches(a -> !a.getAuthorName().equals(""));
    }

    @DisplayName("должен загружать список всех авторов с полной информацией о них")
    @Transactional
    @Test
    void returnCorrectAuthorsListWithAllInfo() {
        List<Author> author = repositoryJpa.findAll();
        assertThat(author).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .allMatch(a -> !a.getAuthorName().equals(""));
    }

    @DisplayName("должен загружать информацию об авторе по его имени")
    @Transactional
    @Test
    void findExpectedAuthorByName() {
        Author firstAuthor = em.find(Author.class, AUTHOR_ID);
        Optional<Author> author = repositoryJpa.findByName(FIRST_AUTHOR_NAME);

        assertThat(author).isPresent().get().isEqualTo(firstAuthor);
    }

    @DisplayName("должен изменять имя автора по его id")
    @Transactional
    @Test
    void updateAuthorNameById() {
        Author firstAuthor = em.find(Author.class, AUTHOR_ID);
        String oldName = firstAuthor.getAuthorName();
        em.detach(firstAuthor);

        firstAuthor.setAuthorName(AUTHOR_NAME);
        repositoryJpa.save(firstAuthor);
        Author updatedAuthor = em.find(Author.class, AUTHOR_ID);

        assertThat(updatedAuthor.getAuthorName()).isNotEqualTo(oldName).isEqualTo(AUTHOR_NAME);
    }

    @Transactional
    @DisplayName("должен удалять несвязанного автора по его id")
    @Test
    void deleteAuthorById() {
        Author author = new Author(AUTHOR_NAME);
        repositoryJpa.save(author);
        assertThat(author.getId()).isGreaterThan(0);
        repositoryJpa.deleteById(author.getId());

        Author deletedAuthor = em.find(Author.class, author.getId());
        assertThat(deletedAuthor).isNull();
    }
}