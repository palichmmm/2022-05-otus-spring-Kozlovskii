package ru.otus.spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @EqualsAndHashCode.Exclude
    private Book book;

    public Comment(Book book, String comment) {
        this.book = book;
        this.comment = comment;
    }

    public Comment(long id, String comment) {
        this.id = id;
        this.comment = comment;
    }
}
