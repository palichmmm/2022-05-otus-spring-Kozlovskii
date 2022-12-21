package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;
import ru.otus.spring.service.IOService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommandsBook {
    private final BookService service;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final IOService ioService;

    @ShellMethod(key = {"b", "book"}, value = "One book: <command> [id]")
    public void showOneBook(String id) {
        ioService.outputString("Запрос в базу книг по ID=" + id);
        ioService.outputString(String.valueOf(service.findById(id)));
    }

    @ShellMethod(key = {"bn", "book-name"}, value = "Books by name: <command> [name]")
    public void showBooks(String name) {
        ioService.outputString("Запрос в базу книг по NAME=" + name);
        if (service.findByName(name).isEmpty()) {
            ioService.outputString("Книги с названием " + name + " не существует!!!");
        } else {
            service.findByName(name).forEach(book -> ioService.outputString(String.valueOf(book)));
        }
    }

    @ShellMethod(key = {"bb", "books"}, value = "List of books: <command>")
    public void showAllBook() {
        ioService.outputString("Список книг в базе: ");
        service.findAll().forEach(book -> ioService.outputString(String.valueOf(book)));
    }

    @ShellMethod(key = {"bd", "book-delete"}, value = "Delete book: <command> [id]")
    public void showDeleteBook(String id) {
        ioService.outputString("Удаляем книгу из базы с id=" + id);
        service.deleteById(id);
    }

    @ShellMethod(key = {"bi", "book-insert"}, value = "Insert book: <command> [bookName] [author id] [genre id]")
    public void showInsertBook(String bookName, String authorId, String genreId) {
        ioService.outputString("Вставляем книгу(" + bookName + ") в базу:");
        Author author = authorService.findById(authorId);
        Genre genre = genreService.findById(genreId);
        ioService.outputString(String.valueOf(service.save(new Book(bookName, author, genre))));
    }

    @ShellMethod(key = {"bu", "book-update"}, value = "Update book: <command> [id] [name]")
    public void showUpdateBook(String id, String newNameBook) {
        ioService.outputString("Обновляем название книги в базе с ID=" + id);
        ioService.outputString(String.valueOf(service.save(new Book(id, newNameBook))));
    }
}
