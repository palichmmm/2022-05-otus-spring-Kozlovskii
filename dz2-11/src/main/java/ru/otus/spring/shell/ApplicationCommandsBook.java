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
    public static final String INPUT_TEMPLATE_COMMAND = "<command>";
    public static final String INPUT_TEMPLATE_COMMAND_ID = "<command> [id]";
    public static final String INPUT_TEMPLATE_COMMAND_NAME = "<command> [name]";
    public static final String INPUT_TEMPLATE_COMMAND_ID_NAME = "<command> [id] [name]";
    public static final String STRING_DEFAULT_NAME = "By sea away";
    private final CRUDModelBook book;
    private final IOService ioService;

    @ShellMethod(key = {"b", "book"}, value = "One book: " + INPUT_TEMPLATE_COMMAND_ID)
    public String showOneBook(@ShellOption(defaultValue = STRING_DEFAULT_ID) long id) {
        ioService.outputString("Запрос в базу книг по ID=" + id);
        book.showById(id);
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"bn", "book-name"}, value = "Books by name: " + INPUT_TEMPLATE_COMMAND_NAME)
    public String showBooks(@ShellOption(defaultValue = STRING_DEFAULT_NAME) String name) {
        ioService.outputString("Запрос в базу книг по NAME=" + name);
        book.showByName(name);
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"bb", "books"}, value = "List of books: " + INPUT_TEMPLATE_COMMAND)
    public String showAllBook() {
        ioService.outputString("Список книг в базе: ");
        book.showAll();
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"bc", "comments-book"}, value = "All book comments: " + INPUT_TEMPLATE_COMMAND_ID)
    public String showAllBookComments(@ShellOption(defaultValue = STRING_DEFAULT_ID) long id) {
        ioService.outputString("Все комментарии книги с ID=" + id + " в базе: ");
        book.showAllCommentsBookById(id);
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"bd", "book-delete"}, value = "Delete book: " + INPUT_TEMPLATE_COMMAND_ID)
    public String showDeleteBook(long id) {
        ioService.outputString("Удаляем книгу из базы с id=" + id);
        book.deleteById(id);
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"bi", "book-insert"}, value = "Insert book: <command> [name] [author id] [genre id]")
    public String showInsertBook(@ShellOption(defaultValue = "DEFAULT-BOOK-NAME") String bookName, long authorId, long genreId) {
        ioService.outputString("Вставляем книгу(" + bookName + ") в базу:");
        book.create(new Book(bookName, new Author(authorId), new Genre(genreId)));
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"bu", "book-update"}, value = "Update book: " + INPUT_TEMPLATE_COMMAND_ID_NAME)
    public String showUpdateBook(long id, String newNameBook) {
        ioService.outputString("Обновляем название книги в базе с ID=" + id);
        book.update(new Book(id, newNameBook));
        return COMMAND_COMPLETED;
    }
}
