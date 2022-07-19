package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private long id;
    private String nameBook;
    private Author author;
    private List<Genre> listGenre;
}
