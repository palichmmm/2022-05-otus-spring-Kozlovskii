package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private long id;
    private long authorId;
    private long genreId;
    @NotBlank(message = "Где название книги?")
    @Size(max = 40, message = "Остановись писатель! Максимум 40 букв!")
    private String bookName;
    private String author;
    private String genre;

    // Сделан для теста на всякий случай)
    public BookDto(long authorId, long genreId, String bookName) {
        this.authorId = authorId;
        this.genreId = genreId;
        this.bookName = bookName;
    }

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getAuthor().getId(), book.getGenre().getId(), book.getBookName(), book.getAuthor().getAuthorName(), book.getGenre().getGenreName());
    }

    public static Book toBook(BookDto dto, Book book) {
        book.setBookName(dto.getBookName());
        book.setAuthor(new Author(dto.getAuthorId()));
        book.setGenre(new Genre(dto.getGenreId()));
        return book;
    }
}
