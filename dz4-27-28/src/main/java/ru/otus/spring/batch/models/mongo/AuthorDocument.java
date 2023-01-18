package ru.otus.spring.batch.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "authors")
public class AuthorDocument {
    @Id
    private String id;
    private long originalId;
    private String authorName;

    public AuthorDocument(long originalId, String authorName) {
        this.originalId = originalId;
        this.authorName = authorName;
    }
}
