package ru.otus.spring.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.models.Genre;

import java.util.ArrayList;
import java.util.List;

@ChangeLog(order = "001")
public class DataBaseChangeLogLibraryBook {

    private Author georgEliot;
    private Author virginiaWoolf;
    private Author charlesDickens;

    private Genre detective;
    private Genre novel;
    private Genre adventures;

    private final List<Book> books = new ArrayList<>();

    @ChangeSet(author = "palichmmm@gmail.com", id = "DbDrop", order = "001", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(author = "palichmmm@gmail.com", id = "saveAuthor", order = "002", runAlways = true)
    public void insertAuthors(MongockTemplate template) {
        georgEliot = template.save(new Author("George Eliot"));
        virginiaWoolf = template.save(new Author("Virginia Woolf"));
        charlesDickens = template.save(new Author("Charles Dickens"));
    }

    @ChangeSet(author = "palichmmm@gmail.com", id = "saveGenre", order = "003", runAlways = true)
    public void insertGenres(MongockTemplate template) {
        detective = template.save(new Genre("Detective"));
        novel = template.save(new Genre("Novel"));
        adventures = template.save(new Genre("Adventures"));
    }

    @ChangeSet(author = "palichmmm@gmail.com", id = "saveBook", order = "004", runAlways = true)
    public void insertBooks(MongockTemplate template) {
        books.add(template.save(new Book("Adam Beed", georgEliot, novel)));
        books.add(template.save(new Book("Mill on the Floss", georgEliot, novel)));
        books.add(template.save(new Book("By sea away", virginiaWoolf, detective)));
        books.add(template.save(new Book("Day and night", virginiaWoolf, detective)));
        books.add(template.save(new Book("Posthumous Papers of the Pickwick Club", charlesDickens, adventures)));
        books.add(template.save(new Book("The Adventures of Oliver Twist", charlesDickens, adventures)));
    }

    @ChangeSet(author = "palichmmm@gmail.com", id = "saveComment", order = "005", runAlways = true)
    public void insertComments(MongockTemplate template) {
        template.save(new Comment("Хорошая", books.get(0)));
        template.save(new Comment("Ну так себе", books.get(0)));
        template.save(new Comment("Отличная", books.get(1)));
        template.save(new Comment("Скучная", books.get(1)));
        template.save(new Comment("Шедевр", books.get(2)));
        template.save(new Comment("Легко читается", books.get(2)));
        template.save(new Comment("Отстой", books.get(3)));
        template.save(new Comment("Мне понравилось", books.get(3)));
        template.save(new Comment("Хорошая книга рекомендую", books.get(4)));
        template.save(new Comment("Супер!!!", books.get(4)));
        template.save(new Comment("Скучная", books.get(5)));
        template.save(new Comment("Читал в захлеб)", books.get(5)));
    }
}
