package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

@Service
public class ApplicationLauncher implements Launcher {

    public final String TEST_NAME_AUTHOR;
    public final String TEST_NAME_GENRE;
    public final String TEST_NAME_BOOK;
    public final long LONG_ID_TABLE;
    private final IOService ioService;
    private final CRUDModelBook book;
    private final CRUDModelAuthor author;
    private final CRUDModelGenre genre;

    public ApplicationLauncher(@Value("${test.name.author}") String test_name_author,
                               @Value("${test.name.genre}") String test_name_genre,
                               @Value("${test.name.book}") String test_name_book,
                               @Value("${test.table.id}") long long_id_table, IOService ioService,
                               CRUDModelBook book,
                               CRUDModelAuthor author,
                               CRUDModelGenre genre) {
        TEST_NAME_AUTHOR = test_name_author;
        TEST_NAME_GENRE = test_name_genre;
        TEST_NAME_BOOK = test_name_book;
        LONG_ID_TABLE = long_id_table;
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

        ioService.outputString("\nЗапрос в базу авторов по id=" + LONG_ID_TABLE);
        ioService.outputString(author.readById(LONG_ID_TABLE).toString());

        ioService.outputString("\nЗапрос в базу жанров по id=" + LONG_ID_TABLE);
        ioService.outputString(genre.readById(LONG_ID_TABLE).toString());

        ioService.outputString("\nЗапрос в базу книг по id=" + LONG_ID_TABLE);
        ioService.outputString(book.readById(LONG_ID_TABLE).toString());

        ioService.outputString("\nСоздать запись автора(" + TEST_NAME_AUTHOR + ") в базу");
        long resultA = author.create(new Author(0, TEST_NAME_AUTHOR));
        ioService.outputString(resultA == -1 ? "Ошибка создания записи автора!!!" : "Создан автор с ID=" + resultA);

        ioService.outputString("\nСоздать запись жанра(" + TEST_NAME_GENRE + ") в базу");
        long resultG = genre.create(new Genre(0, TEST_NAME_GENRE));
        ioService.outputString(resultG == -1 ? "Ошибка создания записи жанра!!!" : "Создан жанр с ID=" + resultG);

        ioService.outputString("\nСоздать запись книги(" + TEST_NAME_BOOK + ") в базу");
        long resultB = book.create(new Book(0, TEST_NAME_BOOK, new Author(1, ""), new Genre(2, "")));
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
