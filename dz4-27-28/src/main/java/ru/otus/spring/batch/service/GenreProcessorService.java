package ru.otus.spring.batch.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.batch.models.mongo.GenreDocument;
import ru.otus.spring.models.Genre;

@Service
public class GenreProcessorService {

    public GenreDocument mapInGenreDocument(Genre genre) {
        return new GenreDocument(genre.getId(), genre.getGenreName());
    }
}
