package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class BookLibrary {
    private int id;
    private String nameLibrary;
    private List<Book> bookList;
}
