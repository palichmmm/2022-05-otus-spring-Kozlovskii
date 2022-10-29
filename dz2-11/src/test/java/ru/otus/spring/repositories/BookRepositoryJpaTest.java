package ru.otus.spring.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.spring.repositories.GenreRepositoryJpaTest.GENRE_ID;

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
@Import(BookRepositoryJpa.class)
public class BookRepositoryJpaTest {
    public static final long BOOK_ID = 1L;
    public static final long AUTHOR_ID = 1L;
    public static final String BOOK_NAME = "Evil Under the Sun";
    public static final int EXPECTED_NUMBER_OF_BOOKS = 6;
    public static final String FIRST_BOOK_NAME = "Adam Beed";
    @Autowired
    private BookRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать информацию о нужной книге по ее id")
    @Test
    void findExpectedBookById() {
        Optional<Book> actualBook = repositoryJpa.findById(BOOK_ID);
        Book expectedBook = em.find(Book.class, BOOK_ID);
        assertThat(actualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("должен корректно сохранять всю информацию о книге")
    @Test
    void saveAllBookInfo() {
        Author author = em.find(Author.class, AUTHOR_ID);
        Genre genre = em.find(Genre.class, GENRE_ID);
        Book book = new Book(BOOK_NAME, author, genre);
        repositoryJpa.save(book);
        assertThat(book.getId()).isGreaterThan(0);

        Book actualBook = em.find(Book.class, book.getId());
        assertThat(actualBook).isNotNull()
                .matches(b -> !b.getBookName().equals(""))
                .matches(b -> b.getAuthor() != null && b.getId() > 0)
                .matches(b -> b.getGenre() != null && b.getId() > 0);
    }

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void returnCorrectBookListWithAllInfo() {
        List<Book> books = repositoryJpa.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(b -> !b.getBookName().equals(""))
                .allMatch(b -> b.getAuthor() != null && b.getId() > 0)
                .allMatch(b -> b.getGenre() != null && b.getId() > 0)
                .allMatch(b -> b.getComments() != null && b.getId() > 0);
    }

    @DisplayName("должен загружать информацию о нужной книге по ее названию")
    @Test
    void findExpectedBookByName() {
        Book firstBook = em.find(Book.class, BOOK_ID);
        List<Book> Books = repositoryJpa.findByName(FIRST_BOOK_NAME);

        assertThat(Books).containsOnlyOnce(firstBook);
    }

    @DisplayName("должен изменять название книги по ее id")
    @Test
    void updateBookNameById() {
        Book firstBook = em.find(Book.class, BOOK_ID);
        String oldName = firstBook.getBookName();
        em.detach(firstBook);

        firstBook.setBookName(BOOK_NAME);
        repositoryJpa.save(firstBook);
        Book updatedBook = em.find(Book.class, BOOK_ID);

        assertThat(updatedBook.getBookName()).isNotEqualTo(oldName).isEqualTo(BOOK_NAME);
    }

    @DisplayName("должен удалять книгу по ее id")
    @Test
    void deleteBookById() {
        Book firstBook = em.find(Book.class, BOOK_ID);
        assertThat(firstBook).isNotNull();
        repositoryJpa.deleteById(firstBook.getId());

        Book deletedBook = em.find(Book.class, BOOK_ID);
        assertThat(deletedBook).isNull();
    }
}

