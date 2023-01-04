package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.models.Author;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.IOService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommandsAuthor {
    private final AuthorService service;
    private final IOService ioService;

    @ShellMethod(key = {"a", "author"}, value = "One author: <command> [id]")
    public void showAuthorById(String id) {
        ioService.outputString("Запрос в базу авторов по ID=" + id);
        ioService.outputString(String.valueOf(service.findById(id)));
    }

    @ShellMethod(key = {"an", "author-name"}, value = "One author by name: <command> [name]")
    public void showOneAuthorByName(String name) {
        ioService.outputString("Запрос в базу авторов по NAME=" + name);
        if (service.findByName(name).isEmpty()) {
            ioService.outputString("Автора с именем " + name + " не существует!!!");
        } else {
            service.findByName(name).forEach(author -> ioService.outputString(String.valueOf(author)));
        }
    }

    @ShellMethod(key = {"aa", "authors"}, value = "List of authors: <command>")
    public void showAllAuthor() {
        ioService.outputString("Список авторов в базе: ");
        service.findAll().forEach(author -> ioService.outputString(String.valueOf(author)));
    }

    @ShellMethod(key = {"ad", "author-delete"}, value = "Delete author: <command> [id]")
    public void showDeleteAuthor(String id) {
        ioService.outputString("Удаляем автора из базы с ID=" + id);
        service.deleteById(id);
    }

    @ShellMethod(key = {"ai", "author-insert"}, value = "Insert author: <command> [name]")
    public void showInsertAuthor(String authorName) {
        ioService.outputString("Вставляем автора(" + authorName + ") в базу:");
        ioService.outputString(String.valueOf(service.save(new Author(authorName))));
    }

    @ShellMethod(key = {"au", "author-update"}, value = "Update author: <command> [id] [name]")
    public void showUpdateAuthor(String id, String newNameAuthor) {
        ioService.outputString("Обновляем имя автора в базе с ID=" + id);
        ioService.outputString(String.valueOf(service.save(new Author(id, newNameAuthor))));
    }
}
