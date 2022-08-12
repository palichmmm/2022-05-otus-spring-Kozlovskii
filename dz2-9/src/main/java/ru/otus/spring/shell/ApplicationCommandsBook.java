package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.models.Book;
import ru.otus.spring.service.CRUDModelBookServiceImpl;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommandsBook {
    public static final String COMMAND_COMPLETED = "Команда завершена";
    public static final String STRING_DEFAULT_ID = "1";
    private final CRUDModelBookServiceImpl book;
    @ShellMethod(key = {"b", "book"}, value = "One book")
    public String showOneBook(@ShellOption(defaultValue = STRING_DEFAULT_ID) String id) {
        System.out.println("Запрос в базу книг по id=" + id);
        System.out.println(book.readById(Long.parseLong(id)));
        return COMMAND_COMPLETED;
    }
    @ShellMethod(key = {"bb", "books"}, value = "List of books")
    public String showAllBook() {
        System.out.println("Список книг в базе: ");
        for (Book tempBook : book.readAll()) {
            System.out.println(tempBook);
        }
        return COMMAND_COMPLETED;
    }
    @ShellMethod(key = {"bd", "book-delete"}, value = "Delete book")
    public String showDeleteBook(String id) {
        System.out.println("Удаляем книгу из базы с id=" + id);
        if(book.deleteById(Long.parseLong(id))) {
            System.out.println("Книга с id=" + id + " успешно удалена!");
        } else {
            System.out.println("Ошибка при удалении! Возможна есть ссылка на строку в другой таблице!");
        }
        return COMMAND_COMPLETED;
    }
    @ShellMethod(key = {"bi", "book-insert"}, value = "Insert book")
    public String showInsertBook(@ShellOption(defaultValue = "DEFAULT-BOOK-NAME") String bookName) {
        System.out.println("Вставляем книгу в базу:");
        long resultId = book.create(new Book(0, bookName, null, null));
        System.out.println("Вставлена новая запись книги с ID=" + resultId);
        return COMMAND_COMPLETED;
    }
    @ShellMethod(key = {"bu", "book-update"}, value = "Update book")
    public String showUpdateBook(String id) {
        System.out.println("Обновляем книгу в базе:");
        return "Обновлена запись книга - ";
    }
}
