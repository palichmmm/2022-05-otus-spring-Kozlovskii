package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private long id;
    @NotBlank(message = "Кому нужен пустой отзыв?")
    @Size(max = 20, message = "Остановись писатель! Максимум 20 букв!")
    private String comment;
    private long bookId;
    private String bookName;
}
