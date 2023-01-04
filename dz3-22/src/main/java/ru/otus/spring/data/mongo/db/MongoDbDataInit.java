package ru.otus.spring.data.mongo.db;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.models.Genre;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile({"dev", "test"})
public class MongoDbDataInit {

    private Author georgEliot;
    private Author virginiaWoolf;
    private Author charlesDickens;

    private Genre detective;
    private Genre novel;
    private Genre adventures;

    private final List<Book> books = new ArrayList<>();

    private final ReactiveMongoTemplate template;

    public MongoDbDataInit(ReactiveMongoTemplate template) {
        this.template = template;
    }

    public void run() {
        dropCollections();
        insertAuthors();
        insertGenres();
        insertBooks();
        insertComments();
    }

    public void dropCollections() {
        template.dropCollection("authors").block();
        template.dropCollection("genres").block();
        template.dropCollection("books").block();
        template.dropCollection("comments").block();
    }

    public void insertAuthors() {
        georgEliot = template.insert(new Author("George Eliot")).block();
        virginiaWoolf = template.insert(new Author("Virginia Woolf")).block();
        charlesDickens = template.insert(new Author("Charles Dickens")).block();
    }

    public void insertGenres() {
        detective = template.insert(new Genre("Detective")).block();
        novel = template.insert(new Genre("Novel")).block();
        adventures = template.insert(new Genre("Adventures")).block();
    }

    public void insertBooks() {
        books.add(template.insert(new Book("Adam Beed", georgEliot, novel)).block());
        books.add(template.insert(new Book("Mill on the Floss", georgEliot, novel)).block());
        books.add(template.insert(new Book("By sea away", virginiaWoolf, detective)).block());
        books.add(template.insert(new Book("Day and night", virginiaWoolf, detective)).block());
        books.add(template.insert(new Book("Posthumous Papers of the Pickwick Club", charlesDickens, adventures)).block());
        books.add(template.insert(new Book("The Adventures of Oliver Twist", charlesDickens, adventures)).block());
    }

    public void insertComments() {
        template.insert(new Comment("Хорошая", books.get(0))).block();
        template.insert(new Comment("Ну так себе", books.get(0))).block();
        template.insert(new Comment("Отличная", books.get(1))).block();
        template.insert(new Comment("Скучная", books.get(1))).block();
        template.insert(new Comment("Шедевр", books.get(2))).block();
        template.insert(new Comment("Легко читается", books.get(2))).block();
        template.insert(new Comment("Отстой", books.get(3))).block();
        template.insert(new Comment("Мне понравилось", books.get(3))).block();
        template.insert(new Comment("Хорошая книга рекомендую", books.get(4))).block();
        template.insert(new Comment("Супер!!!", books.get(4))).block();
        template.insert(new Comment("Скучная", books.get(5))).block();
        template.insert(new Comment("Читал в захлеб)", books.get(5))).block();
    }
}
