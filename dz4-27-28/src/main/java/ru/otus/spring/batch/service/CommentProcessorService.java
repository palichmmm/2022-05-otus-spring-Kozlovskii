package ru.otus.spring.batch.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.batch.models.mongo.CommentDocument;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;

@Service
public class CommentProcessorService {

    public CommentDocument mapInCommentDocument(Comment comment) {
        return new CommentDocument(comment.getComment(), new Book());
    }
}
