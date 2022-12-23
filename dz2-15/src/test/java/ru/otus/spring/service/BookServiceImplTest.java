package ru.otus.spring.service;

import org.assertj.core.api.Assertions;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.models.Book;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.GenreRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
@ComponentScan({"ru.otus.spring.service"})
@EnableConfigurationProperties
class BookServiceImplTest {

    @Autowired
    private MongoTemplate template;

    @Autowired
    private BookService service;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("Должен вернуть правильное количество записей книг в Db")
    @Test
    void shouldReturnRightNumberOfRecordsDb() {
        long expected = 6;
        long count = template.getDb().getCollection("books").countDocuments(Document.parse("{}"));
        Assertions.assertThat(count).isEqualTo(expected);
    }

    @DisplayName("Должен возвращать книгу и выдавать ошибку если ее нет")
    @Test
    void shouldReturnBookAndThrowAnErrorIfThereIsNone() {
        String expectedBook = "Adam Beed";
        List<Book> bookByName = service.findByName(expectedBook);
        Book bookById = service.findById(bookByName.get(0).getId());
        Assertions.assertThat(bookById).isEqualTo(bookByName.get(0));

        String nonExistentId = "gDi8HF6HJ";
        Throwable exception = assertThrows(RuntimeException.class, () -> service.findById(nonExistentId));
        assertEquals("КНИГИ С ID - " + nonExistentId + " НЕ СУЩЕСТВУЕТ!", exception.getMessage());
    }

    @DisplayName("Должен сохранять книгу в Db")
    @Test
    void shouldSaveBookInDb() {
        long count = template.getDb().getCollection("books").countDocuments(Document.parse("{}"));

        Book book = new Book("Книга",
                authorRepository.findByAuthorName("George Eliot").get(0),
                genreRepository.findByGenreName("Novel").get(0));
        Book saveBook = service.save(book);
        book.setId(saveBook.getId());
        assertEquals(book, saveBook);

        long countAfter = template.getDb().getCollection("books").countDocuments(Document.parse("{}"));
        Assertions.assertThat(countAfter).isEqualTo(count + 1);
    }

    @DisplayName("Должен удалять книгу и все комментарии к ней")
    @Test
    void shouldDeleteOrThrowErrorIfAuthorIsLinked() {
        long count = template.getDb().getCollection("books").countDocuments(Document.parse("{}"));

        Book book = new Book("Книга",
                authorRepository.findByAuthorName("George Eliot").get(0),
                genreRepository.findByGenreName("Novel").get(0));
        Book saveBook = service.save(book);

        long countAfter = template.getDb().getCollection("books").countDocuments(Document.parse("{}"));
        Assertions.assertThat(countAfter).isEqualTo(count + 1);

        service.deleteById(saveBook.getId());

        long countEnd = template.getDb().getCollection("books").countDocuments(Document.parse("{}"));
        Assertions.assertThat(countEnd).isEqualTo(count);
    }
}