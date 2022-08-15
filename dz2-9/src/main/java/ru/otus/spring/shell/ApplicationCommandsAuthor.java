package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.models.Author;
import ru.otus.spring.service.CRUDModelAuthorServiceImpl;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommandsAuthor {

    public static final String COMMAND_COMPLETED = "Команда завершена";
    public static final String STRING_DEFAULT_ID = "1";
    private final CRUDModelAuthorServiceImpl author;
    @ShellMethod(key = {"a", "author"}, value = "One author: <command> [id]")
    public String showOneAuthor(@ShellOption(defaultValue = STRING_DEFAULT_ID) String id) {
        System.out.println("Запрос в базу авторов по id=" + id);
        System.out.println(author.readById(Integer.parseInt(id)));
        return COMMAND_COMPLETED;
    }
    @ShellMethod(key = {"aa", "authors"}, value = "List of authors: <command>")
    public String showAllAuthor() {
        System.out.println("Список авторов в базе: ");
        for (Author tempAuthor : author.readAll()) {
            System.out.println(tempAuthor);
        }
        return COMMAND_COMPLETED;
    }
    @ShellMethod(key = {"ad", "author-delete"}, value = "Delete author: <command> [id]")
    public String showDeleteAuthor(String id) {
        System.out.println("Удаляем автора из базы с id=" + id);
        if(author.deleteById(Long.parseLong(id))) {
            System.out.println("Автор с id=" + id + " успешно удален!");
        } else {
            System.out.println("Ошибка при удалении! Возможна есть ссылка на строку в другой таблице!");
        }
        return COMMAND_COMPLETED;
    }
    @ShellMethod(key = {"ai", "author-insert"}, value = "Insert author: <command> [name]")
    public String showInsertAuthor(@ShellOption(defaultValue = "DEFAULT-AUTHOR-NAME") String authorName) {
        System.out.println("Вставляем автора(" + authorName + ") в базу:");
        long resultId = author.create(new Author(0,authorName));
        if (resultId == -1) {
            System.out.println("Неудалось вставить автора с именем " + authorName + " Возможно такой автор уже есть!");
        } else {
            System.out.println("Вставлена новая запись автора с ID=" + resultId);
        }
        return COMMAND_COMPLETED;
    }
    @ShellMethod(key = {"au", "author-update"}, value = "Update author: <command> [id] [new name]")
    public String showUpdateAuthor(String id, String newNameAuthor) {
        System.out.println("Обновляем имя автора в базе с ID=" + id);
        if (author.update(new Author(Long.parseLong(id), newNameAuthor))) {
            System.out.println("Автор с ID=" + id + " успешно обновлен!");
        } else {
            System.out.println("Не удалось обновить Автора с ID=" + id);
        }
        return COMMAND_COMPLETED;
    }
}
