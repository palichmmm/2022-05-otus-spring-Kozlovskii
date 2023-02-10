package ru.otus.spring.integration.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

import java.util.List;

@MessagingGateway
public interface LetterGateway {

    @Gateway(requestChannel = "authorChannel", replyChannel = "outputAuthorChannel")
    List<Author> authorReplacementLetters(List<Author> list);

    @Gateway(requestChannel = "genreChannel", replyChannel = "outputGenreChannel")
    List<Genre> genreReplacementLetters(List<Genre> list);

    @Gateway(requestChannel = "bookFlow.input")
    List<Book> bookReplacementLetters(List<Book> list);

}
