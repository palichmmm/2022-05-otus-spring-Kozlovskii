package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private long id;
    @NotBlank(message = "Где название книги?")
    @Size(max = 40, message = "Остановись писатель! Максимум 40 букв!")
    private String bookName;
    @NotBlank(message = "Почему автор безымянный?")
    @Size(max = 20, message = "Больше 20 букв - это не автор!")
    private String author;
    @NotBlank(message = "Придумай хоть что нибудь!")
    @Size(max = 11, message = "Хватит строчить! Максимум 11 букв!")
    private String genre;
}
