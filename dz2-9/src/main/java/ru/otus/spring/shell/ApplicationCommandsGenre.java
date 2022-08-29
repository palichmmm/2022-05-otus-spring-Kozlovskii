package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.models.Genre;
import ru.otus.spring.service.CRUDModelGenre;
import ru.otus.spring.service.IOService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommandsGenre {
    public static final String COMMAND_COMPLETED = "Команда завершена";
    public static final String STRING_DEFAULT_ID = "1";
    private final CRUDModelGenre genre;

    private final IOService ioService;

    @ShellMethod(key = {"g", "genre"}, value = "One genre: <command> [id]")
    public String showOneGenre(@ShellOption(defaultValue = STRING_DEFAULT_ID) long id) {
        ioService.outputString("Запрос в базу жанров по id=" + id);
        ioService.outputString(String.valueOf(genre.readById(id)));
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"gg", "genres"}, value = "List of genres: <command>")
    public String showAllGenre() {
        ioService.outputString("Список жанров в базе: ");
        for (Genre tempGenre : genre.readAll()) {
            ioService.outputString(String.valueOf(tempGenre));
        }
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"gd", "genre-delete"}, value = "Delete genre: <command> [id]")
    public String showDeleteGenre(long id) {
        ioService.outputString("Удаляем жанр из базы с id=" + id);
        if (genre.deleteById(id)) {
            ioService.outputString("Жанр с id=" + id + " успешно удален!");
        } else {
            ioService.outputString("Ошибка при удалении!");
        }
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"gi", "genre-insert"}, value = "Insert genre: <command> [name]")
    public String showInsertGenre(@ShellOption(defaultValue = "DEFAULT-GENRE-NAME") String genreName) {
        ioService.outputString("Вставляем жанр(" + genreName + ") в базу:");
        long resultId = genre.create(new Genre(0, genreName));
        if (resultId == -1) {
            ioService.outputString("Неудалось вставить жанр с именем " + genreName + " Возможно такой жанр уже есть!");
        } else {
            ioService.outputString("Вставлена новая запись жанра с ID=" + resultId);
        }
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"gu", "genre-update"}, value = "Update genre: <command> [id] [new name]")
    public String showUpdateGenre(long id, String newNameGenre) {
        ioService.outputString("Обновляем имя жанра в базе с ID=" + id);
        if (genre.update(new Genre(id, newNameGenre))) {
            ioService.outputString("Жанр с ID=" + id + " успешно обновлен!");
        } else {
            ioService.outputString("Не удалось обновить Жанр с ID=" + id);
        }
        return COMMAND_COMPLETED;
    }
}
