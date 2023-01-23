package ru.otus.spring.batch.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.batch.models.mongo.AuthorDocument;
import ru.otus.spring.batch.models.mongo.BookDocument;
import ru.otus.spring.batch.models.mongo.GenreDocument;
import ru.otus.spring.models.Book;

@Service
public class BookProcessorService {

    public BookDocument mapInBookDocument(Book book) {
        return new BookDocument(book.getBookName(),
                new AuthorDocument(book.getAuthor().getAuthorName()),
                new GenreDocument(book.getGenre().getGenreName()));
    }
}
