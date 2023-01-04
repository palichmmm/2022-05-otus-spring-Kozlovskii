package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private String id;
    private String comment;
    private String bookId;
    private String bookName;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getComment(), comment.getBook().getId(), comment.getBook().getBookName());
    }

    public static Comment toComment(Book book, CommentDto dto) {
        return new Comment(dto.getId(), dto.getComment(), book);
    }
}
