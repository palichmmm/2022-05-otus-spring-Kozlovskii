package ru.otus.spring.batch.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "genres")
public class GenreDocument {
    @Id
    private String id;
    private String genreName;

    public GenreDocument(String genreName) {
        this.genreName = genreName;
    }
}
