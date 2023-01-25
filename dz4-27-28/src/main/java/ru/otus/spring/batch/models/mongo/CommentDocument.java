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
@Document(collection = "comments")
public class CommentDocument {
    @Id
    private String id;
    private String comment;
    @DBRef
    private BookDocument book;

    public CommentDocument(String comment, BookDocument book) {
        this.comment = comment;
        this.book = book;
    }
}
