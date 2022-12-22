package ru.otus.spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "authors")
public class Author {
    @Id
    private String id;
    private String authorName;

    public Author(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "{Id=" + id + "   " + authorName + '}';
    }
}
