package ru.otus.spring.service;

import org.assertj.core.api.Assertions;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.models.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
@EnableConfigurationProperties
@Import(AuthorServiceImpl.class)
class AuthorServiceImplTest {

    @Autowired
    private MongoTemplate template;

    @Autowired
    private AuthorService service;

    @DisplayName("Должен вернуть правильное количество записей авторов в Db")
    @Test
    void shouldReturnRightNumberOfRecordsDb() {
        long expected = 3;
        long count = template.getDb().getCollection("authors").countDocuments(Document.parse("{}"));
        Assertions.assertThat(count).isEqualTo(expected);
    }

    @DisplayName("Должен возвращать автора и выдавать ошибку если его нет")
    @Test
    void shouldReturnAuthorAndThrowAnErrorIfThereIsNone() {
        String expectedAuthor = "George Eliot";
        List<Author> authorByName = service.findByName(expectedAuthor);
        Author authorById = service.findById(authorByName.get(0).getId());
        Assertions.assertThat(authorById).isEqualTo(authorByName.get(0));

        String nonExistentId = "gDi8HF6HJ";
        Throwable exception = assertThrows(RuntimeException.class, () -> service.findById(nonExistentId));
        assertEquals("АВТОР С ID - " + nonExistentId + " НЕ СУЩЕСТВУЕТ!", exception.getMessage());
    }

    @DisplayName("Должен сохранять автора в Db")
    @Test
    void shouldSaveAuthorInDb() {
        long count = template.getDb().getCollection("authors").countDocuments(Document.parse("{}"));

        Author author = new Author("Пушкин");
        Author saveAuthor = service.save(author);
        author.setId(saveAuthor.getId());
        assertEquals(author, saveAuthor);

        long countAfter = template.getDb().getCollection("authors").countDocuments(Document.parse("{}"));
        Assertions.assertThat(countAfter).isEqualTo(count + 1);
    }

    @DisplayName("Должен удалять или выдавать ошибку если автор связан")
    @Test
    void shouldDeleteOrThrowErrorIfAuthorIsLinked() {
        Author author = new Author("Пушкин");
        List<Author> linkedAuthor = service.findByName("George Eliot");

        long count = template.getDb().getCollection("authors").countDocuments(Document.parse("{}"));

        Author saveAuthor = service.save(author);

        long countAfter = template.getDb().getCollection("authors").countDocuments(Document.parse("{}"));
        Assertions.assertThat(countAfter).isEqualTo(count + 1);

        service.deleteById(saveAuthor.getId());

        long countEnd = template.getDb().getCollection("authors").countDocuments(Document.parse("{}"));
        Assertions.assertThat(countEnd).isEqualTo(count);

        Throwable exception = assertThrows(RuntimeException.class, () -> service.deleteById(linkedAuthor.get(0).getId()));
        assertEquals("Ошибка удаления! На этого автора ссылаются книги!", exception.getMessage());
    }
}