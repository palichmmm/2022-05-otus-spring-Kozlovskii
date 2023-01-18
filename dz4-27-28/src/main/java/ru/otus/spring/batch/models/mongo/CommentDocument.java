package ru.otus.spring.batch.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
public class CommentDocument {
    @Id
    private String id;
    private long originalId;
    private String comment;
    private BookDocument book;

    public CommentDocument(long originalId, String comment, BookDocument book) {
        this.originalId = originalId;
        this.comment = comment;
        this.book = book;
    }
}
