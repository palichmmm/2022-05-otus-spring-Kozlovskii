package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.models.Book;
import ru.otus.spring.repositories.BookRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Data Jpa для работы с книгами ")
@DataJpaTest
public class BookRepositoryJpaTest {
    public static final long BOOK_ID = 1L;
    public static final String FIRST_BOOK_NAME = "Adam Beed";
    @Autowired
    private BookRepository repositoryJpa;
    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать информацию о нужной книге по ее названию")
    @Test
    void findExpectedBookByName() {
        Book firstBook = em.find(Book.class, BOOK_ID);
        List<Book> Books = repositoryJpa.findBookByBookName(FIRST_BOOK_NAME);

        assertThat(Books).containsOnlyOnce(firstBook);
    }
}

