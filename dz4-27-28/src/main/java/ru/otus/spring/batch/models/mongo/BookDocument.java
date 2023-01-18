package ru.otus.spring.batch.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class BookDocument {
    @Id
    private String id;
    private long originalId;
    private String bookName;
    private AuthorDocument author;
    private GenreDocument genre;

    public BookDocument(String id) {
        this.id = id;
    }

    public BookDocument(long originalId, String bookName, AuthorDocument author, GenreDocument genre) {
        this.originalId = originalId;
        this.bookName = bookName;
        this.author = author;
        this.genre = genre;
    }
}
