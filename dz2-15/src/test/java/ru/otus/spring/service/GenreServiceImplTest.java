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
import ru.otus.spring.models.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
@ComponentScan({"ru.otus.spring.service"})
@EnableConfigurationProperties
class GenreServiceImplTest {

    @Autowired
    private MongoTemplate template;

    @Autowired
    private GenreService service;

    @DisplayName("Должен вернуть правильное количество записей жанров в Db")
    @Test
    void shouldReturnRightNumberOfRecordsDb() {
        long expected = 3;
        long count = template.getDb().getCollection("genres").countDocuments(Document.parse("{}"));
        Assertions.assertThat(count).isEqualTo(expected);
    }

    @DisplayName("Должен возвращать жанр и выдавать ошибку если его нет")
    @Test
    void shouldReturnGenreAndThrowAnErrorIfThereIsNone() {
        String expectedGenre = "Novel";
        List<Genre> genreByName = service.findByName(expectedGenre);
        Genre genreById = service.findById(genreByName.get(0).getId());
        Assertions.assertThat(genreById).isEqualTo(genreByName.get(0));

        String nonExistentId = "gDi8HF6HJ";
        Throwable exception = assertThrows(RuntimeException.class, () -> service.findById(nonExistentId));
        assertEquals("ЖАНРА С ID - " + nonExistentId + " НЕ СУЩЕСТВУЕТ!", exception.getMessage());
    }

    @DisplayName("Должен сохранять жанр в Db")
    @Test
    void shouldSaveGenreInDb() {
        long count = template.getDb().getCollection("genres").countDocuments(Document.parse("{}"));

        Genre genre = new Genre("History");
        Genre saveGenre = service.save(genre);
        genre.setId(saveGenre.getId());
        assertEquals(genre, saveGenre);

        long countAfter = template.getDb().getCollection("genres").countDocuments(Document.parse("{}"));
        Assertions.assertThat(countAfter).isEqualTo(count + 1);
    }

    @DisplayName("Должен удалять или выдавать ошибку если жанр связан")
    @Test
    void shouldDeleteOrThrowErrorIfGenreIsLinked() {
        Genre genre = new Genre("History");
        List<Genre> linkedGenre = service.findByName("Novel");

        long count = template.getDb().getCollection("genres").countDocuments(Document.parse("{}"));

        Genre saveGenre = service.save(genre);

        long countAfter = template.getDb().getCollection("genres").countDocuments(Document.parse("{}"));
        Assertions.assertThat(countAfter).isEqualTo(count + 1);

        service.deleteById(saveGenre.getId());

        long countEnd = template.getDb().getCollection("genres").countDocuments(Document.parse("{}"));
        Assertions.assertThat(countEnd).isEqualTo(count);

        Throwable exception = assertThrows(RuntimeException.class, () -> service.deleteById(linkedGenre.get(0).getId()));
        assertEquals("Ошибка удаления! На этот жанр ссылаются книги!", exception.getMessage());
    }
}