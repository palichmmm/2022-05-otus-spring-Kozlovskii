package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

@Service
public class ApplicationLaunch implements Launch{

    public String TEST_NAME_AUTHOR = "TEST-NAME-AUTHOR";
    public String TEST_NAME_GENRE = "TEST-NAME-GENRE";
    public String TEST_NAME_BOOK = "TEST-NAME-BOOK";
    public static final int LONG_ID_TABLE = 1;
    private final IOService ioService = new IOServiceStreamsImpl(System.out, System.in);
    private final CRUDModelBookServiceImpl book;
    private final CRUDModelAuthorServiceImpl author;
    private final CRUDModelGenreServiceImpl genre;

    public ApplicationLaunch(CRUDModelBookServiceImpl book, CRUDModelAuthorServiceImpl author, CRUDModelGenreServiceImpl genre) {
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
        long resultB = book.create(new Book(0, TEST_NAME_BOOK, null, null));
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
