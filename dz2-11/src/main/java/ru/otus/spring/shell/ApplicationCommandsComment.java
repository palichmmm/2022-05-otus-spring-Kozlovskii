package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.service.CommentService;
import ru.otus.spring.service.IOService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommandsComment {
    public static final String COMMAND_COMPLETED = "Команда завершена";
    public static final String STRING_DEFAULT_ID = "1";
    public static final String INPUT_TEMPLATE_COMMAND_ID = "<command> [id]";
    public static final String INPUT_TEMPLATE_COMMAND_ID_NAME = "<command> [id] [name]";
    private final CommentService comment;
    private final IOService ioService;

    @ShellMethod(key = {"c", "comment"}, value = "One comment: " + INPUT_TEMPLATE_COMMAND_ID)
    public String showOneComment(@ShellOption(defaultValue = STRING_DEFAULT_ID) long id) {
        ioService.outputString("Запрос в базу комментариев по ID=" + id);
        comment.showById(id);
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"cd", "comment-delete"}, value = "Delete comment: " + INPUT_TEMPLATE_COMMAND_ID)
    public String showDeleteComment(long id) {
        ioService.outputString("Удаляем комментарий из базы с ID=" + id);
        comment.deleteById(id);
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"ci", "comment-insert"}, value = "Insert comment: " + INPUT_TEMPLATE_COMMAND_ID_NAME)
    public String showInsertComment(@ShellOption(defaultValue = "DEFAULT-COMMENT-NAME") long id, String commentName) {
        ioService.outputString("Вставляем комментарий(" + commentName + ") в базу:");
        comment.create(new Comment(new Book(id), commentName));
        return COMMAND_COMPLETED;
    }

    @ShellMethod(key = {"cu", "comment-update"}, value = "Update comment: " + INPUT_TEMPLATE_COMMAND_ID_NAME)
    public String showUpdateComment(long id, String newComment) {
        ioService.outputString("Обновляем комментарий в базе с ID=" + id);
        comment.update(id, newComment);
        return COMMAND_COMPLETED;
    }
}
