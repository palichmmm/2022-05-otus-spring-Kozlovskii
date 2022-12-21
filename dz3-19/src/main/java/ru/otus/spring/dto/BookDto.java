package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
