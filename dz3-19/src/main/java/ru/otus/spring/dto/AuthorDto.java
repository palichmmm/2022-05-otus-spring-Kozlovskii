package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.models.Author;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class AuthorDto {
    private long id;

    @NotBlank(message = "Почему автор безымянный?")
    @Size(max = 20, message = "Больше 20 букв - это не автор!")
    private String authorName;

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getAuthorName());
    }

    public static Author toAuthor(AuthorDto dto) {
        return new Author(dto.getId(), dto.getAuthorName());
    }
}
