package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

@Service
public class ApplicationLaunch implements Launch{

    public String TEST_NAME_AUTHOR = "TEST-NAME-AUTHOR";
    public String TEST_NAME_GENRE = "TEST-NAME-GENRE";
    public String TEST_NAME_BOOK = "TEST-NAME-AUTHOR";
    public static final int INT_ID_TABLE = 1;
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

        ioService.outputString("\nЗапрос в базу авторов по id=" + INT_ID_TABLE);
        ioService.outputString(author.read(INT_ID_TABLE).toString());

        ioService.outputString("\nЗапрос в базу жанров по id=" + INT_ID_TABLE);
        ioService.outputString(genre.read(INT_ID_TABLE).toString());

        ioService.outputString("\nЗапрос в базу книг по id=" + INT_ID_TABLE);
        ioService.outputString(book.read(INT_ID_TABLE).toString());

        ioService.outputString("\nСоздать запись автора(" + TEST_NAME_AUTHOR + ") в базу");
        int result = author.create(new Author(555, TEST_NAME_AUTHOR));
        ioService.outputString(result == -1 ? "Ошибка создания записи автора!!!" : "Создан автор с ID=" + result);
    }
}
