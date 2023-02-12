package ru.otus.spring.batch.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.batch.models.mongo.AuthorDocument;
import ru.otus.spring.models.Author;

@Service
public class AuthorProcessorService {

    public AuthorDocument mapInAuthorDocument(Author author) {
        return new AuthorDocument(author.getAuthorName());
    }
}
