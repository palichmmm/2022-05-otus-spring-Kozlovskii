package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.models.Author;
import ru.otus.spring.service.CRUDModelAuthor;
import ru.otus.spring.service.IOService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommandsAuthor {
    public static final String COMMAND_COMPLETED = "Команда завершена";
    public static final String STRING_DEFAULT_ID = "1";
    public static final String STRING_DEFAULT_NAME = "George Eliot";
    public static final String INPUT_TEMPLATE_COMMAND = "<command>";
    public static final String INPUT_TEMPLATE_COMMAND_ID = "<command> [id]";
    public static final String INPUT_TEMPLATE_COMMAND_NAME = "<command> [name]";
    public static final String INPUT_TEMPLATE_COMMAND_ID_NAME = "<command> [id] [name]";
    private final CRUDModelAuthor author;
    private final IOService ioService;

    @ShellMethod(key = {"a", "author"}, value = "One author: " + INPUT_TEMPLATE_COMMAND_ID)
    public String showOneAuthor(@ShellOption(defaultValue = STRING_DEFAULT_ID) long id) {
        ioService.outputString("Запрос в базу авторов по ID=" + id);
        author.showById(id);
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"an", "author-name"}, value = "One author by name: " + INPUT_TEMPLATE_COMMAND_NAME)
    public String showOneAuthor(@ShellOption(defaultValue = STRING_DEFAULT_NAME) String name) {
        ioService.outputString("Запрос в базу авторов по NAME=" + name);
        author.showByName(name);
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"aa", "authors"}, value = "List of authors: " + INPUT_TEMPLATE_COMMAND)
    public String showAllAuthor() {
        ioService.outputString("Список авторов в базе: ");
        author.showAll();
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"ad", "author-delete"}, value = "Delete author: " + INPUT_TEMPLATE_COMMAND_ID)
    public String showDeleteAuthor(long id) {
        ioService.outputString("Удаляем автора из базы с ID=" + id);
        author.deleteById(id);
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"ai", "author-insert"}, value = "Insert author: " + INPUT_TEMPLATE_COMMAND_NAME)
    public String showInsertAuthor(@ShellOption(defaultValue = "DEFAULT-AUTHOR-NAME") String authorName) {
        ioService.outputString("Вставляем автора(" + authorName + ") в базу:");
        author.create(new Author(authorName));
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"au", "author-update"}, value = "Update author: " + INPUT_TEMPLATE_COMMAND_ID_NAME)
    public String showUpdateAuthor(long id, String newNameAuthor) {
        ioService.outputString("Обновляем имя автора в базе с ID=" + id);
        author.update(new Author(id, newNameAuthor));
        return COMMAND_COMPLETED;
    }
}
