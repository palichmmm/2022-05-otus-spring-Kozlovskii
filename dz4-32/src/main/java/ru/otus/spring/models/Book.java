package ru.otus.spring.models;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
@NamedEntityGraph(name = "author-genre-graph",
        attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("genre")})
public class Book {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "book_name")
    private String bookName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id")
    @EqualsAndHashCode.Exclude
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "genre_id")
    @EqualsAndHashCode.Exclude
    private Genre genre;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "book")
    @Fetch(FetchMode.SUBSELECT)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    public Book(long id) {
        this.id = id;
    }

    public Book(String bookName, Author author, Genre genre) {
        this.bookName = bookName;
        this.author = author;
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(bookName, book.bookName) && Objects.equals(author, book.author) && Objects.equals(genre, book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookName, author, genre);
    }
}
