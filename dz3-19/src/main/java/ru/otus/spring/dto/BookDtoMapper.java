package ru.otus.spring.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

@Component
@RequiredArgsConstructor
public class BookDtoMapper {

    public BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getAuthor().getId(),
                book.getGenre().getId(),
                book.getBookName(),
                book.getAuthor().getAuthorName(),
                book.getGenre().getGenreName());
    }

    public Book toBook(BookDto dto, Book book) {
        book.setId(dto.getId());
        book.setBookName(dto.getBookName());
        book.setAuthor(new Author(dto.getAuthorId(), dto.getAuthor()));
        book.setGenre(new Genre(dto.getGenreId(), dto.getGenre()));
        return book;
    }
}
