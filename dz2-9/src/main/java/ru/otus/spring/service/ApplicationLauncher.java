package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

@Service
public class ApplicationLauncher implements Launcher {

    private final String testNameAuthor;
    private final String testNameGenre;
    private final String testNameBook;
    private final long longIdTable;
    private final IOService ioService;
    private final CRUDModelBook book;
    private final CRUDModelAuthor author;
    private final CRUDModelGenre genre;

    public ApplicationLauncher(@Value("${test.name.author}") String testNameAuthor,
                               @Value("${test.name.genre}") String testNameGenre,
                               @Value("${test.name.book}") String testNameBook,
                               @Value("${test.table.id}") long longIdTable,
                               IOService ioService,
                               CRUDModelBook book,
                               CRUDModelAuthor author,
                               CRUDModelGenre genre) {
        this.testNameAuthor = testNameAuthor;
        this.testNameGenre = testNameGenre;
        this.testNameBook = testNameBook;
        this.longIdTable = longIdTable;
        this.ioService = ioService;
        this.book = book;
        this.author = author;
        this.genre = genre;
    }

    @Override
    public void run() {

        ioService.outputString("\nВсе авторы в базе: ");
        for (Author author : author.readAll()) {
            ioService.outputString(author.toString());
        }

        ioService.outputString("\nВсе жанры в базе: ");
        for (Genre genre : genre.readAll()) {
            ioService.outputString(genre.toString());
        }

        ioService.outputString("\nВсе книги в базе: ");
        for (Book book : book.readAll()) {
            ioService.outputString(book.toString());
        }

        ioService.outputString("\nЗапрос в базу авторов по id=" + longIdTable);
        ioService.outputString(author.readById(longIdTable).toString());

        ioService.outputString("\nЗапрос в базу жанров по id=" + longIdTable);
        ioService.outputString(genre.readById(longIdTable).toString());

        ioService.outputString("\nЗапрос в базу книг по id=" + longIdTable);
        ioService.outputString(book.readById(longIdTable).toString());

        ioService.outputString("\nСоздать запись автора(" + testNameAuthor + ") в базу");
        long resultA = author.create(new Author(0, testNameAuthor));
        ioService.outputString(resultA == -1 ? "Ошибка создания записи автора!!!" : "Создан автор с ID=" + resultA);

        ioService.outputString("\nСоздать запись жанра(" + testNameGenre + ") в базу");
        long resultG = genre.create(new Genre(0, testNameGenre));
        ioService.outputString(resultG == -1 ? "Ошибка создания записи жанра!!!" : "Создан жанр с ID=" + resultG);

        ioService.outputString("\nСоздать запись книги(" + testNameBook + ") в базу");
        long resultB = book.create(new Book(0, testNameBook, new Author(1, ""), new Genre(2, "")));
        ioService.outputString(resultB == -1 ? "Ошибка создания записи книги!!!" : "Создана книга с ID=" + resultB);

        ioService.outputString("\nУдаление автора с ID=" + resultA);
        if (author.deleteById(resultA)) {
            ioService.outputString("Автор с ID=" + resultA + " успешно удален!");
        } else {
            ioService.outputString("Ошибка удаления!!! Возможна есть ссылка на строку в другой таблице!");
        }

        ioService.outputString("\nУдаление жанра с ID=" + resultG);
        if (genre.deleteById(resultG)) {
            ioService.outputString("Жанр с ID=" + resultG + " успешно удален!");
        } else {
            ioService.outputString("Ошибка удаления!!! Возможна есть ссылка на строку в другой таблице!");
        }

        ioService.outputString("\nУдаление книги с ID=" + resultB);
        if (book.deleteById(resultB)) {
            ioService.outputString("Книга с ID=" + resultB + " успешно удалена!");
        } else {
            ioService.outputString("Ошибка удаления!!!");
        }
    }
}
