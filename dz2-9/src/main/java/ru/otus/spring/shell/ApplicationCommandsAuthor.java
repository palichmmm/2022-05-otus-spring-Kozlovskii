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

    private final CRUDModelAuthor author;

    private final IOService ioService;

    @ShellMethod(key = {"a", "author"}, value = "One author: <command> [id]")
    public String showOneAuthor(@ShellOption(defaultValue = STRING_DEFAULT_ID) long id) {
        ioService.outputString("Запрос в базу авторов по id=" + id);
        ioService.outputString(String.valueOf(author.readById(id)));
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"aa", "authors"}, value = "List of authors: <command>")
    public String showAllAuthor() {
        ioService.outputString("Список авторов в базе: ");
        for (Author tempAuthor : author.readAll()) {
            ioService.outputString(String.valueOf(tempAuthor));
        }
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"ad", "author-delete"}, value = "Delete author: <command> [id]")
    public String showDeleteAuthor(long id) {
        ioService.outputString("Удаляем автора из базы с id=" + id);
        if (author.deleteById(id)) {
            ioService.outputString("Автор с id=" + id + " успешно удален!");
        } else {
            ioService.outputString("Ошибка при удалении! Возможна есть ссылка на строку в другой таблице!");
        }
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"ai", "author-insert"}, value = "Insert author: <command> [name]")
    public String showInsertAuthor(@ShellOption(defaultValue = "DEFAULT-AUTHOR-NAME") String authorName) {
        ioService.outputString("Вставляем автора(" + authorName + ") в базу:");
        long resultId = author.create(new Author(0, authorName));
        if (resultId == -1) {
            ioService.outputString("Неудалось вставить автора с именем " + authorName + " Возможно такой автор уже есть!");
        } else {
            ioService.outputString("Вставлена новая запись автора с ID=" + resultId);
        }
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"au", "author-update"}, value = "Update author: <command> [id] [new name]")
    public String showUpdateAuthor(long id, String newNameAuthor) {
        ioService.outputString("Обновляем имя автора в базе с ID=" + id);
        if (author.update(new Author(id, newNameAuthor))) {
            ioService.outputString("Автор с ID=" + id + " успешно обновлен!");
        } else {
            ioService.outputString("Не удалось обновить Автора с ID=" + id);
        }
        return COMMAND_COMPLETED;
    }
}
