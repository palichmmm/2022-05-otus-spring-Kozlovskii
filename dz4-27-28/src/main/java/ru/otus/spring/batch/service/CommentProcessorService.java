package ru.otus.spring.batch.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.batch.models.mongo.AuthorDocument;
import ru.otus.spring.batch.models.mongo.BookDocument;
import ru.otus.spring.batch.models.mongo.CommentDocument;
import ru.otus.spring.batch.models.mongo.GenreDocument;
import ru.otus.spring.models.Comment;

@Service
public class CommentProcessorService {

    public CommentDocument mapInCommentDocument(Comment comment) {
        BookDocument book = new BookDocument(
                comment.getBook().getBookName(),
                new AuthorDocument(comment.getBook().getAuthor().getAuthorName()),
                new GenreDocument(comment.getBook().getGenre().getGenreName()));
        return new CommentDocument(comment.getComment(), book);
    }
}
