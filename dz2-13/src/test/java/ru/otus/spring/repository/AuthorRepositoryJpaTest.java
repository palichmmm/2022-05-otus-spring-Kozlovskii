package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Author;
import ru.otus.spring.repositories.AuthorRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с авторами ")
@DataJpaTest
public class AuthorRepositoryJpaTest {
    public static final long AUTHOR_ID = 1L;
    public static final String FIRST_AUTHOR_NAME = "George Eliot";
    @Autowired
    private AuthorRepository repositoryJpa;
    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать информацию об авторе по его имени")
    @Transactional
    @Test
    void findExpectedAuthorByName() {
        Author firstAuthor = em.find(Author.class, AUTHOR_ID);
        Optional<Author> author = repositoryJpa.findByAuthorName(FIRST_AUTHOR_NAME);

        assertThat(author).isPresent().get().isEqualTo(firstAuthor);
    }
}