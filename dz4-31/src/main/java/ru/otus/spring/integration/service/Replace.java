package ru.otus.spring.integration.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.models.Genre;

import java.util.List;

@MessagingGateway
public interface Replace {

    @Gateway(requestChannel = "authorFlow.input")
    List<Author> authorReplacementLetters(List<Author> list);

    @Gateway(requestChannel = "genreFlow.input")
    List<Genre> genreReplacementLetters(List<Genre> list);

    @Gateway(requestChannel = "bookFlow.input")
    List<Book> bookReplacementLetters(List<Book> list);

}
