package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.models.Author;

@Data
@AllArgsConstructor
public class AuthorDto {
    private String id;
    private String authorName;

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getAuthorName());
    }

    public static Author toAuthor(AuthorDto dto) {
        return new Author(dto.getId(), dto.getAuthorName());
    }
}
