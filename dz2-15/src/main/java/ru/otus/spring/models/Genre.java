package ru.otus.spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "genres")
public class Genre {
    @Id
    private String id;
    private String genreName;

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public String toString() {
        return "{Id=" + id + "   " + genreName + '}';
    }
}
