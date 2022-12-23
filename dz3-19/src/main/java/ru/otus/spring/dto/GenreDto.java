package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.models.Genre;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class GenreDto {
    private long id;

    @NotBlank(message = "Придумай хоть что нибудь!")
    @Size(max = 11, message = "Хватит строчить! Максимум 11 букв!")
    private String genreName;

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getGenreName());
    }

    public static Genre toGenre(GenreDto dto) {
        return new Genre(dto.getId(), dto.getGenreName());
    }
}