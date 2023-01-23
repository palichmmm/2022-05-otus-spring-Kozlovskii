package ru.otus.spring.batch.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class BookDocument {
    @Id
    private String id;
    private String bookName;
    @DBRef
    private AuthorDocument author;
    @DBRef
    private GenreDocument genre;

    public BookDocument(String id) {
        this.id = id;
    }

    public BookDocument(String bookName, AuthorDocument author, GenreDocument genre) {
        this.bookName = bookName;
        this.author = author;
        this.genre = genre;
    }
}
