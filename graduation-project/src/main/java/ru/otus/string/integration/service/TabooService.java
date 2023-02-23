package ru.otus.string.integration.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TabooService {

    public static final List<String> tabooList = Arrays.asList("A", "a", "E", "e", "O", "o");

//    public boolean authorTaboo(Author author) {
//        for (String taboo : tabooList) {
//            if (author.getAuthorName().contains(taboo)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean genreTaboo(Genre genre) {
//        for (String taboo : tabooList) {
//            if (genre.getGenreName().contains(taboo)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean bookTaboo(Book book) {
//        for (String taboo : tabooList) {
//            if (book.getBookName().contains(taboo)) {
//                return true;
//            }
//        }
//        return false;
//    }
}
