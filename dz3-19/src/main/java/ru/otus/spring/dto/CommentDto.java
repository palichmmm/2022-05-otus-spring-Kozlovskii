package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private long id;
    @NotBlank(message = "Кому нужен пустой отзыв?")
    @Size(max = 20, message = "Остановись писатель! Максимум 20 букв!")
    private String comment;
    private long bookId;
    private String bookName;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getComment(), comment.getBook().getId(), comment.getBook().getBookName());
    }

    public static Comment toComment(Book book, CommentDto dto) {
        return new Comment(dto.getId(), dto.getComment(), book);
    }
}
