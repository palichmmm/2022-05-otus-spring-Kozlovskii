package ru.otus.spring.batch.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.batch.models.mongo.BookDocument;
import ru.otus.spring.batch.models.mongo.CommentDocument;
import ru.otus.spring.batch.repository.BookMongoRepository;
import ru.otus.spring.models.Comment;

@Service
public class CommentProcessorService {

    private final BookMongoRepository bookMongoRepository;

    public CommentProcessorService(BookMongoRepository bookMongoRepository) {
        this.bookMongoRepository = bookMongoRepository;
    }

    public CommentDocument mapInCommentDocument(Comment comment) {
        BookDocument bookDocument = bookMongoRepository.findBookDocumentByBookName(comment.getBook().getBookName());
        return new CommentDocument(comment.getComment(), bookDocument);
    }
}
