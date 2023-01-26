package ru.otus.spring.integration.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.models.Genre;

import java.util.List;

@Service
public class ReplacementLettersService {

    public List<Author> authorReplacementLetters(List<Author> list) {
        for (Author author : list) {
            author.setAuthorName(author.getAuthorName().replaceAll("[AaEeOo]", "*"));
        }
        return list;
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
