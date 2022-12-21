package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.models.Genre;
import ru.otus.spring.service.GenreService;
import ru.otus.spring.service.IOService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommandsGenre {
    private final GenreService service;
    private final IOService ioService;

    @ShellMethod(key = {"g", "genre"}, value = "One genre: <command> [id]")
    public void showOneGenreById(String id) {
        ioService.outputString("Запрос в базу жанров по ID=" + id);
        ioService.outputString(String.valueOf(service.findById(id)));
    }

    @ShellMethod(key = {"gn", "genre-name"}, value = "One genre by name: <command> [name]")
    public void showOneGenreByName(String name) {
        ioService.outputString("Запрос в базу жанров по NAME=" + name);
        if (service.findByName(name).isEmpty()) {
            ioService.outputString("Жанра " + name + " не существует!!!");
        } else {
            service.findByName(name).forEach(genre -> ioService.outputString(String.valueOf(genre)));
        }
    }

    @ShellMethod(key = {"gg", "genres"}, value = "List of genres: <command>")
    public void showAllGenre() {
        ioService.outputString("Список жанров в базе: ");
        service.findAll().forEach(genre -> ioService.outputString(String.valueOf(genre)));
    }

    @ShellMethod(key = {"gd", "genre-delete"}, value = "Delete genre: <command> [id]")
    public void showDeleteGenre(String id) {
        ioService.outputString("Удаляем жанр из базы с ID=" + id);
        service.deleteById(id);
    }

    @ShellMethod(key = {"gi", "genre-insert"}, value = "Insert genre: <command> [name]")
    public void showInsertGenre(String genreName) {
        ioService.outputString("Вставляем жанр(" + genreName + ") в базу:");
        ioService.outputString(String.valueOf(service.save(new Genre(genreName))));
    }

    @ShellMethod(key = {"gu", "genre-update"}, value = "Update genre: <command> [id] [name]")
    public void showUpdateGenre(String id, String newNameGenre) {
        ioService.outputString("Обновляем имя жанра в базе с ID=" + id);
        ioService.outputString(String.valueOf(service.save(new Genre(id, newNameGenre))));
    }
}
