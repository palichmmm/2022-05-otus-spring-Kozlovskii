package ru.otus.spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Название жанра не должно быть пустым!")
    @Size(min = 0, max = 255, message = "Название жанра превышает допустимый размер!")
    @Column(name = "genre_name", nullable = false, unique = true)
    private String genreName;

    public Genre(long id) {
        this.id = id;
    }

    public Genre(String genreName) {
        this.genreName = genreName;
    }
}
