package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.models.Genre;

@Data
@AllArgsConstructor
public class GenreDto {
    private String id;
    private String genreName;

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getGenreName());
    }

    public static Genre toGenre(GenreDto dto) {
        return new Genre(dto.getId(), dto.getGenreName());
    }
}