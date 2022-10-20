package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.models.Genre;
import ru.otus.spring.service.GenreService;
import ru.otus.spring.service.IOService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommandsGenre {
    public static final String COMMAND_COMPLETED = "Команда завершена";
    public static final String STRING_DEFAULT_ID = "1";
    public static final String STRING_DEFAULT_NAME = "Detective";
    public static final String INPUT_TEMPLATE_COMMAND = "<command>";
    public static final String INPUT_TEMPLATE_COMMAND_ID = "<command> [id]";
    public static final String INPUT_TEMPLATE_COMMAND_NAME = "<command> [name]";
    public static final String INPUT_TEMPLATE_COMMAND_ID_NAME = "<command> [id] [name]";
    private final GenreService genre;
    private final IOService ioService;

    @ShellMethod(key = {"g", "genre"}, value = "One genre: " + INPUT_TEMPLATE_COMMAND_ID)
    public String showOneGenre(@ShellOption(defaultValue = STRING_DEFAULT_ID) long id) {
        ioService.outputString("Запрос в базу жанров по ID=" + id);
        genre.showById(id);
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"gn", "genre-name"}, value = "One genre by name: " + INPUT_TEMPLATE_COMMAND_NAME)
    public String showOneGenre(@ShellOption(defaultValue = STRING_DEFAULT_NAME) String name) {
        ioService.outputString("Запрос в базу жанров по NAME=" + name);
        genre.showByName(name);
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"gg", "genres"}, value = "List of genres: " + INPUT_TEMPLATE_COMMAND)
    public String showAllGenre() {
        ioService.outputString("Список жанров в базе: ");
        genre.showAll();
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"gd", "genre-delete"}, value = "Delete genre: " + INPUT_TEMPLATE_COMMAND_ID)
    public String showDeleteGenre(long id) {
        ioService.outputString("Удаляем жанр из базы с ID=" + id);
        genre.deleteById(id);
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"gi", "genre-insert"}, value = "Insert genre: " + INPUT_TEMPLATE_COMMAND_NAME)
    public String showInsertGenre(@ShellOption(defaultValue = "DEFAULT-GENRE-NAME") String genreName) {
        ioService.outputString("Вставляем жанр(" + genreName + ") в базу:");
        genre.create(new Genre(genreName));
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"gu", "genre-update"}, value = "Update genre: " + INPUT_TEMPLATE_COMMAND_ID_NAME)
    public String showUpdateGenre(long id, String newNameGenre) {
        ioService.outputString("Обновляем имя жанра в базе с ID=" + id);
        genre.update(id, newNameGenre);
        return COMMAND_COMPLETED;
    }
}
