package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.service.CRUDModelBook;
import ru.otus.spring.service.IOService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommandsBook {
    public static final String COMMAND_COMPLETED = "Команда завершена";
    public static final String STRING_DEFAULT_ID = "1";
    private final CRUDModelBook book;

    private final IOService ioService;

    @ShellMethod(key = {"b", "book"}, value = "One book: <command> [id]")
    public String showOneBook(@ShellOption(defaultValue = STRING_DEFAULT_ID) long id) {
        ioService.outputString("Запрос в базу книг по id=" + id);
        ioService.outputString(String.valueOf(book.readById(id)));
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"bb", "books"}, value = "List of books: <command>")
    public String showAllBook() {
        ioService.outputString("Список книг в базе: ");
        for (Book tempBook : book.readAll()) {
            ioService.outputString(String.valueOf(tempBook));
        }
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"bd", "book-delete"}, value = "Delete book: <command> [id]")
    public String showDeleteBook(long id) {
        ioService.outputString("Удаляем книгу из базы с id=" + id);
        if (book.deleteById(id)) {
            ioService.outputString("Книга с id=" + id + " успешно удалена!");
        } else {
            ioService.outputString("Ошибка при удалении! Возможна есть ссылка на строку в другой таблице!");
        }
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"bi", "book-insert"}, value = "Insert book: <command> [name] [author id] [genre id]")
    public String showInsertBook(@ShellOption(defaultValue = "DEFAULT-BOOK-NAME") String bookName, String authorId, String genreId) {
        ioService.outputString("Вставляем книгу(" + bookName + ") в базу:");
        long resultId = book.create(new Book(0, bookName, new Author(Long.parseLong(authorId), ""), new Genre(Long.parseLong(genreId), "")));
        if (resultId == -1) {
            ioService.outputString("Неудалось вставить книгу с именем " + bookName + " Возможно такая книга уже есть!");
        } else {
            ioService.outputString("Вставлена новая запись книги с ID=" + resultId);
        }
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"bu", "book-update"}, value = "Update book: <command> [id] [author id] [genre id] [new name]")
    public String showUpdateBook(long id, String authorId, String genreId, String newNameBook) {
        ioService.outputString("Обновляем название книги в базе с ID=" + id);
        if (book.update(new Book(id, newNameBook, new Author(Long.parseLong(authorId), ""), new Genre(Long.parseLong(genreId), "")))) {
            ioService.outputString("Книга с ID=" + id + " успешно обновлена!");
        } else {
            ioService.outputString("Не удалось обновить Книгу с ID=" + id);
        }
        return COMMAND_COMPLETED;
    }
}
