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
@Table(name = "authors")
public class Author {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Имя автора не должно быть пустым!")
    @Size(min = 0, max = 255, message = "Имя автора превышает допустимый размер!")
    @Column(name = "author_name", nullable = false, unique = true)
    private String authorName;

    public Author(long id) {
        this.id = id;
    }

    public Author(String authorName) {
        this.authorName = authorName;
    }
}
