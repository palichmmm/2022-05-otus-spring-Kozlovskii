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
    @NotBlank(message = "Почему автор безымянный?")
    @Size(max = 20, message = "Больше 20 букв - это не автор!")
    private String author;
    @NotBlank(message = "Придумай хоть что нибудь!")
    @Size(max = 11, message = "Хватит строчить! Максимум 11 букв!")
    private String genre;

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getAuthor().getId(), book.getGenre().getId(), book.getBookName(), book.getAuthor().getAuthorName(), book.getGenre().getGenreName());
    }

    public static Book toBook(BookDto dto) {
        return new Book(dto.getId(), dto.bookName, new Author(dto.getAuthorId()), new Genre(dto.getGenreId()), null);
    }
}
