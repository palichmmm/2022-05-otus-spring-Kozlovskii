package ru.otus.spring.healthchecks;

import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;
import ru.otus.spring.service.GenreService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component("modelsdb")
@ConditionalOnEnabledHealthIndicator("modelsdb")
public class CountModelsInDbHealthIndicator implements HealthIndicator {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final CommentService commentService;

    public CountModelsInDbHealthIndicator(AuthorService authorService, GenreService genreService,
                                          BookService bookService, CommentService commentService) {
        this.authorService = authorService;
        this.genreService = genreService;
        this.bookService = bookService;
        this.commentService = commentService;
    }
    @Override
    public Health health() {
        long authorCount = authorService.count();
        long genreCount = genreService.count();
        long bookCount = bookService.count();
        long commentCount = commentService.count();
        SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss  dd.MM.yyyy");
        if (authorCount == 0 | genreCount == 0 | bookCount == 0 | commentCount == 0) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("Author", authorCount)
                    .withDetail("Genre", genreCount)
                    .withDetail("Book", bookCount)
                    .withDetail("Comment", commentCount)
                    .withDetail("Date", date.format(new Date()))
                    .build();
        } else {
            return Health.up()
                    .status(Status.UP)
                    .withDetail("Author", authorCount)
                    .withDetail("Genre", genreCount)
                    .withDetail("Book", bookCount)
                    .withDetail("Comment", commentCount)
                    .withDetail("Date", date.format(new Date()))
                    .build();
        }
    }
}
