package ru.otus.spring.integration.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

import java.util.List;

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

    public List<Genre> genreReplacementLetters(List<Genre> list) {
        for (Genre genre : list) {
            genre.setGenreName(genre.getGenreName().replaceAll("[AaEeOo]", "*"));
        }
        return list;
    }

    public List<Book> bookReplacementLetters(List<Book> list) {
        for (Book book : list) {
            book.setBookName(book.getBookName().replaceAll("[AaEeOo]", "*"));
        }
        return list;
    }
}
