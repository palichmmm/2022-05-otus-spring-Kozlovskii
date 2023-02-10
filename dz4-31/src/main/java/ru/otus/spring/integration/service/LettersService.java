package ru.otus.spring.integration.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

@Service
public class LettersService {

    public Author authorReplacementLetters(Author author) {
        String newName = author.getAuthorName();
        for (String taboo : TabooService.tabooList) {
            newName = newName.replaceAll(taboo, "*");
        }
        author.setAuthorName(newName);
        return author;
    }

    public Genre genreReplacementLetters(Genre genre) {
        String newName = genre.getGenreName();
        for (String taboo : TabooService.tabooList) {
            newName = newName.replaceAll(taboo, "*");
        }
        genre.setGenreName(newName);
        return genre;
    }

    public Book bookReplacementLetters(Book book) {
        String newName = book.getBookName();
        for (String taboo : TabooService.tabooList) {
            newName = newName.replaceAll(taboo, "*");
        }
        book.setBookName(newName);
        return book;
    }
}
