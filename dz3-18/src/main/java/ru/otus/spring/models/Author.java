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

    @NotBlank(message = "Почему автор безымянный?")
    @Size(max = 20, message = "Больше 20 букв - это не автор!")
    @Column(name = "author_name", nullable = false, unique = true)
    private String authorName;

    public Author(long id) {
        this.id = id;
    }

    public Author(String authorName) {
        this.authorName = authorName;
    }
}
