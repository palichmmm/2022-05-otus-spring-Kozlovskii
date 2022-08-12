package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Genre;
import ru.otus.spring.service.CRUDModelGenreServiceImpl;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommandsGenre {
    public static final String COMMAND_COMPLETED = "Команда завершена";
    public static final String STRING_DEFAULT_ID = "1";
    private final CRUDModelGenreServiceImpl genre;
    @ShellMethod(key = {"g", "genre"}, value = "One genre")
    public String showOneGenre(@ShellOption(defaultValue = STRING_DEFAULT_ID) String id) {
        System.out.println("Запрос в базу жанров по id=" + id);
        System.out.println(genre.readById(Integer.parseInt(id)));
        return COMMAND_COMPLETED;
    }
    @ShellMethod(key = {"gg", "genres"}, value = "List of genres")
    public String showAllGenre() {
        System.out.println("Список жанров в базе: ");
        for (Genre tempGenre : genre.readAll()) {
            System.out.println(tempGenre);
        }
        return COMMAND_COMPLETED;
    }
    @ShellMethod(key = {"gd", "genre-delete"}, value = "Delete genre")
    public String showDeleteGenre(String id) {
        System.out.println("Удаляем жанр из базы с id=" + id);
        if(genre.deleteById(Integer.parseInt(id))) {
            System.out.println("Жанр с id=" + id + " успешно удален!");
        } else {
            System.out.println("Ошибка при удалении!");
        }
        return COMMAND_COMPLETED;
    }
    @ShellMethod(key = {"gi", "genre-insert"}, value = "Insert genre")
    public String showInsertGenre(@ShellOption(defaultValue = "DEFAULT-GENRE-NAME") String genreName) {
        System.out.println("Вставляем жанр(" + genreName + ") в базу:");
        long resultId = genre.create(new Genre(0,genreName));
        if (resultId == -1) {
            System.out.println("Неудалось вставить жанр с именем " + genreName + " Возможно такой жанр уже есть!");
        } else {
            System.out.println("Вставлена новая запись жанра с ID=" + resultId);
        }
        return COMMAND_COMPLETED;
    }
    @ShellMethod(key = {"gu", "genre-update"}, value = "Update genre")
    public String showUpdateGenre(String id, String newNameGenre) {
        System.out.println("Обновляем имя жанра в базе с ID=" + id);
        if (genre.update(new Genre(Long.parseLong(id), newNameGenre))) {
            System.out.println("Жанр с ID=" + id + " успешно обновлен!");
        } else {
            System.out.println("Не удалось обновить Жанр с ID=" + id);
        }
        return COMMAND_COMPLETED;
    }
}
