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
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
@EnableConfigurationProperties
@Import({CommentServiceImpl.class, BookServiceImpl.class})
class CommentServiceImplTest {

    @Autowired
    private MongoTemplate template;

    @Autowired
    private CommentService service;

    @Autowired
    private BookService bookService;

    @DisplayName("Должен вернуть правильное количество записей комментариев в Db")
    @Test
    void shouldReturnRightNumberOfRecordsDb() {
        long expected = 12;
        long count = template.getDb().getCollection("comments").countDocuments(Document.parse("{}"));
        Assertions.assertThat(count).isEqualTo(expected);
    }

    @DisplayName("Должен возвращать комментарий и выдавать ошибку если его нет")
    @Test
    void shouldReturnCommentAndThrowAnErrorIfThereIsNone() {
        String expectedComment = "Хорошая";
        Book book = bookService.findByName("Adam Beed").get(0);

        List<Comment> allCommentByBook = service.findAllCommentByBookId(book.getId());
        Comment commentById = service.findById(allCommentByBook.get(0).getId());
        Assertions.assertThat(expectedComment).isEqualTo(commentById.getComment());

        String nonExistentId = "gDi8HF6HJ";
        Throwable exception = assertThrows(RuntimeException.class, () -> service.findById(nonExistentId));
        assertEquals("КОММЕНТАРИЙ С ID - " + nonExistentId + " НЕ СУЩЕСТВУЕТ!", exception.getMessage());
    }

    @DisplayName("Должен сохранять комментарий по книге в Db")
    @Test
    void shouldSaveCommentBookInDb() {
        long count = template.getDb().getCollection("comments").countDocuments(Document.parse("{}"));

        Book book = bookService.findByName("Adam Beed").get(0);
        Comment comment = new Comment(null, "Отличная", book);
        Comment saveComment = service.save(comment);
        comment.setId(saveComment.getId());
        assertEquals(comment, saveComment);

        long countAfter = template.getDb().getCollection("comments").countDocuments(Document.parse("{}"));
        Assertions.assertThat(countAfter).isEqualTo(count + 1);
    }

    @DisplayName("Должен удалять комментарий")
    @Test
    void shouldDeleteComment() {
        long count = template.getDb().getCollection("comments").countDocuments(Document.parse("{}"));

        Book book = bookService.findByName("Adam Beed").get(0);
        List<Comment> allCommentByBook = service.findAllCommentByBookId(book.getId());
        service.deleteById(allCommentByBook.get(0).getId());

        long countAfter = template.getDb().getCollection("comments").countDocuments(Document.parse("{}"));
        Assertions.assertThat(countAfter).isEqualTo(count - 1);
    }
}