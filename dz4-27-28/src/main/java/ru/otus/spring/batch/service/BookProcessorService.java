package ru.otus.spring.batch.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.batch.models.mongo.AuthorDocument;
import ru.otus.spring.batch.models.mongo.BookDocument;
import ru.otus.spring.batch.models.mongo.GenreDocument;
import ru.otus.spring.batch.repository.AuthorMongoRepository;
import ru.otus.spring.batch.repository.GenreMongoRepository;
import ru.otus.spring.models.Book;

@Service
public class BookProcessorService {

    private final AuthorMongoRepository authorMongoRepository;

    private final GenreMongoRepository genreMongoRepository;

    public BookProcessorService(AuthorMongoRepository authorMongoRepository, GenreMongoRepository genreMongoRepository) {
        this.authorMongoRepository = authorMongoRepository;
        this.genreMongoRepository = genreMongoRepository;
    }

    public BookDocument mapInBookDocument(Book book) {
        AuthorDocument authorDocument = authorMongoRepository.findAuthorDocumentByAuthorName(book.getAuthor().getAuthorName());
        GenreDocument genreDocument = genreMongoRepository.findGenreDocumentByGenreName(book.getGenre().getGenreName());
        return new BookDocument(book.getBookName(),
                authorDocument,
                genreDocument);
    }
}
