package ru.otus.spring.domain;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Book {
    private final long id;
    private final String nameBook;
    private final Author author;
    private final List<Genre> listGenre;
}
