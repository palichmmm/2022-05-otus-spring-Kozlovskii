package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.service.CommentService;
import ru.otus.spring.service.IOService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommandsComment {
    private final CommentService service;
    private final IOService ioService;

    @ShellMethod(key = {"c", "comment"}, value = "One comment: <command> [id]")
    public void showOneComment(String id) {
        ioService.outputString("Запрос в базу комментариев по ID=" + id);
        ioService.outputString(String.valueOf(service.findById(id)));
    }

    @ShellMethod(key = {"cb", "all-comment-book"}, value = "All comment by book id: <command> [bookId]")
    public void showAllCommentBookById(String bookId) {
        ioService.outputString("Запрос в базу комментариев по ID=" + bookId);
        service.findAllCommentByBook(new Book(bookId)).forEach(comment -> ioService.outputString(String.valueOf(comment)));
    }

    @ShellMethod(key = {"cd", "comment-delete"}, value = "Delete comment: <command> [id]")
    public void showDeleteComment(String id) {
        ioService.outputString("Удаляем комментарий из базы с ID=" + id);
        service.deleteById(id);
    }

    @ShellMethod(key = {"ci", "comment-insert"}, value = "Insert comment: <command> [bookId] [comment]")
    public void showInsertComment(String bookId, String commentName) {
        ioService.outputString("Вставляем комментарий(" + commentName + ") в базу:");
        ioService.outputString(String.valueOf(service.save(new Comment(commentName, new Book(bookId)))));
    }

    @ShellMethod(key = {"cu", "comment-update"}, value = "Update comment: <command> [id] [comment]")
    public void showUpdateComment(String id, String newComment) {
        ioService.outputString("Обновляем комментарий в базе с ID=" + id);
        ioService.outputString(String.valueOf(service.save(new Comment(id, newComment))));
    }
}
