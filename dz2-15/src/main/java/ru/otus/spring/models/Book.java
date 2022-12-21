package ru.otus.spring.models;

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
public class Book {
    @Id
    private String id;
    private String bookName;
    @DBRef
    private Author author;
    @DBRef
    private Genre genre;

    public Book(String id) {
        this.id = id;
    }

    public Book(String id, String bookName) {
        this.id = id;
        this.bookName = bookName;
    }

    public Book(String bookName, Author author, Genre genre) {
        this.bookName = bookName;
        this.author = author;
        this.genre = genre;
    }
}
